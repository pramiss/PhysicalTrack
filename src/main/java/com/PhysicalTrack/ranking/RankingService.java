package com.PhysicalTrack.ranking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.PhysicalTrack.ranking.dto.PushupRankingDto;
import com.PhysicalTrack.records.Record;
import com.PhysicalTrack.records.RecordRepository;
import com.PhysicalTrack.records.RecordService;
import com.PhysicalTrack.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RankingService {

	private final ObjectMapper objectMapper;
	private final RecordService recordService;
	private final UserService userService;
	
	public RankingService(RecordService recordService, UserService userService) {
		this.recordService = recordService;
		this.userService = userService;
		this.objectMapper = new ObjectMapper();
	}
	
	// Pushup Ranking 가져오기 (지난 1달)
	public List<PushupRankingDto> getPushupRanking() {
		
		// 0. sql setting (workoutId, createdAt)
		List<PushupRankingDto> pushupRankingDtos = new ArrayList<>();
		int workoutId = 1;
		LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
		
		// 1. 최근 한달 동안의 pushup 기록을 가져온다. (workoutId = 1)
		List<Record> records = recordService.getPushupRecords(workoutId, oneMonthAgo);
		
		// 2. List<Record> -> List<PushupRankingDto> : userId마다 quantity가 가장 큰 것만.
		for (Record record : records) {
			// userId, quantity
			int userId = record.getUserId();
			int quantity = 0; 
			try {
				quantity = objectMapper.readTree(record.getWorkoutDetail()).get("quantity").asInt();
			} catch (JsonProcessingException e) {
				log.error("JsonProcessingException {workoutDetail : quantity}");
			}
			
			// 0) userId에 해당하는 dto 존재확인
			PushupRankingDto pushupRankingDto = pushupRankingDtos.stream()
											    .filter(dto -> dto.getUserId().equals(userId))
											    .findFirst().orElse(null);
			
			if (pushupRankingDto == null) { // 1) 새로운 userId의 경우 -> new PushupRankingDto()로 dto 리스트에 추가
				pushupRankingDtos.add(new PushupRankingDto(userId, quantity));
			} else if (quantity > pushupRankingDto.getQuantity()) {  // 2) userId 존재 + quantity > 기존 : update
				pushupRankingDto.setQuantity(quantity);
			}

			// 3) userId 존재 + quantity < 기존 : continue
			
		}
		
		// 3. quantitiy 내림차순 정렬
		pushupRankingDtos.sort((a, b) -> b.getQuantity().compareTo(a.getQuantity()));

		// 4. userId에 맞는 set name 및 set rank
		
		// 1) userId 목록 추출
		List<Integer> userIds = pushupRankingDtos.stream()
		    .map(PushupRankingDto::getUserId)
		    .collect(Collectors.toList());

		// 2) 모든 사용자 정보 조회
		Map<Integer, String> userNameMap = userService.getUserNamesByIds(userIds);

		// 3) 메모리에서 매핑
		int rank = 1;
		for (PushupRankingDto dto : pushupRankingDtos) {
		    dto.setName(userNameMap.get(dto.getUserId()));
		    dto.setRank(rank++);
		}
		
		// 5. return
		log.info("@@@@@@@@ {pushupRankingDtos} : " + pushupRankingDtos);
		return pushupRankingDtos;
	}
}
