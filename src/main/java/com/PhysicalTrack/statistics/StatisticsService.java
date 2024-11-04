package com.PhysicalTrack.statistics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.PhysicalTrack.records.RecordService;
import com.PhysicalTrack.records.dto.Record;
import com.PhysicalTrack.statistics.dto.PushupStatsDto;
import com.PhysicalTrack.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatisticsService {

	private final ObjectMapper objectMapper;
	private final RecordService recordService;
	
	public StatisticsService(RecordService recordService) {
		this.recordService = recordService;
		this.objectMapper = new ObjectMapper();
	}
	
	
	
	/**
	 * Get Weekly Pushup Stats
	 * @param userId
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public List<PushupStatsDto> getWeeklyPushupStats(int userId) throws JsonMappingException, JsonProcessingException {
		
		// field
		LocalDateTime oneWeekAgo = LocalDate.now().minusWeeks(1).atStartOfDay();
	    int workoutId = 1;
	    
	    // GET -- Weekly Pushup Records By userId 
	    List<Record> records = recordService.getWeeklyPushupRecordsByWorkoutIduserId(workoutId, userId, oneWeekAgo);
	    
	    // TreeMap 사용 - 키(LocalDate)를 기준으로 자동 오름차순 정렬
	    Map<LocalDate, PushupStatsDto> pushupStatsMap = new TreeMap<>();
	    
	    for (Record record : records) {
	        int quantity = objectMapper.readTree(record.getWorkoutDetail()).get("quantity").asInt();
	        
	        LocalDate recordDate = record.getCreatedAt().toLocalDate();
	        PushupStatsDto newDto = PushupStatsDto.builder()
	                                             .date(recordDate)
	                                             .quantity(quantity)
	                                             .build();
	        
	        pushupStatsMap.compute(recordDate, (date, existingDto) -> 
	            existingDto == null || existingDto.getQuantity() < quantity ? newDto : existingDto
	        );
	    }
	    
	    return new ArrayList<>(pushupStatsMap.values());
	}
}
