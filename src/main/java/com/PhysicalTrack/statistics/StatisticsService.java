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
import com.PhysicalTrack.statistics.dailyStatsDto.DailyStatsDto;
import com.PhysicalTrack.statistics.weeklyStatsDto.PushupStatsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
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
	        
	        LocalDateTime createdAt = record.getCreatedAt();
	        LocalDate recordDate = createdAt.toLocalDate();
	        
	        PushupStatsDto newDto = PushupStatsDto.builder()
	                                             .date(recordDate)
	                                             .createdAt(createdAt)
	                                             .quantity(quantity)
	                                             .build();
	        
	        pushupStatsMap.put(recordDate, newDto);
	    }
	    
	    return new ArrayList<>(pushupStatsMap.values());
	} //-- Get Weekly Pushup Stats
	
	
	/**
	 * Get Daily Pushup Tempo
	 * @param userId
	 * @param date
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public List<Double> getDailyPushupTempo(int userId, LocalDate date) throws JsonMappingException, JsonProcessingException {
		
		// TODO: record가 null 인경우 / 'tempo' 속성이 없는 경우 -> tempo : []
		// TODO: tempo의 타입이 잘못된 경우 (Double List 형태여야한다) -> Exception
		
		// field
	    int workoutId = 1;
	    
	    // GET -- Pushup Record By userId, date
	    Record record = recordService.getDailyPushupRecordByUserIdAndDate(workoutId, userId, date);

	    // List<Double> tempo
	    List<Double> tempo = new ArrayList<>();
	    
	    if (record != null) { // record가 null이면 [ ]
	    	// Pushup Record -> PushupTempoDto
		    JsonNode tempoNode = objectMapper.readTree(record.getWorkoutDetail()).get("tempo");
		    
		    if (tempoNode != null ) { // tempo 속성이 없다면 [ ]
			    for (JsonNode element : tempoNode) {
			    	tempo.add(element.asDouble());
			    }
		    }
	    }
	    
	    
	    // tempo : [] or [2.2, 3.34, 5.03, ...]
	    log.info("\n********** tempo : {}", tempo);
	    
	    return tempo;
	} //-- Get Daily Pushup Tempo
	
}
