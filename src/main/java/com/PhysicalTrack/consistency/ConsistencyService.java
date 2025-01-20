package com.PhysicalTrack.consistency;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.PhysicalTrack.consistency.dto.Consistency;
import com.PhysicalTrack.consistency.dto.ConsistencyDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsistencyService {
	
	private final ConsistencyRepository consistencyRepository;
	private final ObjectMapper objectMapper;
	
	public ConsistencyService(ConsistencyRepository consistencyRepository) {
		this.consistencyRepository = consistencyRepository;
		objectMapper = new ObjectMapper()
			    .findAndRegisterModules()
			    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	/**
	 * 유저의 lastWorkoutDate를 업데이트 -- Record API
	 * @param userId
	 */
	public void updateLastWorkoutDate(int userId) {
		
		// field: 유저 consistency, 업데이트 날짜
		Consistency consistency = consistencyRepository.findById(userId).orElse(null);
		LocalDate updateDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
		
		// 새로운 사용자 추가
		if (consistency == null) {
			Consistency newConsistency = Consistency.builder()
										.userId(userId)
										.streakCount(0)
										.lastWorkoutDate(updateDate)
										.build();
			consistencyRepository.save(newConsistency);
		} 
		// 기존 사용자 업데이트
		else {
			consistency.setLastWorkoutDate(updateDate);
			consistencyRepository.save(consistency);
		}
	}
	
	/**
	 * 모든유저 'streak_count' 업데이트 -- Batch Job
	 */
	public void updateStreakCount() {
		
		// baseline: 어제날짜
		LocalDate baseline = LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(1);
		
		// `consistency`의 모든 튜플들을 불러온다.
		List<Consistency> consistencyList = consistencyRepository.findAll();
		
		for (Consistency consistency : consistencyList) {
			// 튜플의 'last_workout_date' 항목과 baseline을 비교
			boolean flag = consistency.getLastWorkoutDate().equals(baseline);
			int streakCount = consistency.getStreakCount();
			
			// 일치한다면 'streak_count' + 1
			if (flag) {
				log.info("\n $$$$$$$ [userId: {}, streakCount++]", consistency.getUserId());
				streakCount++;
			}
			// 일치하지 않는다면 reset: 0
			else {
				log.info("\n $$$$$$$ [userId: {}, streakCount reset]", consistency.getUserId());
				streakCount = 0;
			}
			
			// streak_count 업데이트
			consistency.setStreakCount(streakCount);
			consistencyRepository.save(consistency);
		} //-- for loop
	} //-- updateStreakCount
	
	// ConsistencyDto 리스트 가져오기 -- Ranking API
	// Consistency -> ConsistencyDto
	public List<ConsistencyDto> getConsistencyDtos() {
		// 1. Consistency 리스트 가져오기
		List<Consistency> consistencies = consistencyRepository.findAllByOrderByStreakCountDesc();
		log.info("&&&&&&&&&&& consistencies : {}", consistencies.toString());
		
		// 2. Consistency -> ConsistencyDto
		List<ConsistencyDto> consistencyDtos = objectMapper.convertValue(
												consistencies, new TypeReference<List<ConsistencyDto>>() {});
		log.info("%%%%%%%%%%% consistencyDtos : {}", consistencyDtos.toString());
		
		// return ConsistencyDto 리스트
		return consistencyDtos;
	} //-- getConsistencyDtos
	
	/**
	 * 회원탈퇴 - 꾸준함 삭제 API
	 * @param userId
	 */
	public void deleteConsistency(int userId) {
		consistencyRepository.deleteByUserId(userId);
	}
	
} //-- ConsistencyService
