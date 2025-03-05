package com.PhysicalTrack.records;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PhysicalTrack.common.ResponseDto;
import com.PhysicalTrack.consistency.ConsistencyService;
import com.PhysicalTrack.records.dto.RecordDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/record/")
@RestController
public class RecordController {

	private final ObjectMapper objectMapper;
	private final RecordService recordService;
	private final ConsistencyService consistencyService;
	
	public RecordController(RecordService recordService, ConsistencyService consistencyService) {
		this.recordService = recordService;
		this.consistencyService = consistencyService;
		this.objectMapper = new ObjectMapper();
	}
	
	/**
	 * pushup을 기록하는 API
	 * @param recordDto
	 * @param request
	 * @return
	 */
	@PostMapping("/pushup")
	public ResponseEntity<?> recordPushup(@RequestBody JsonNode requestBody
			, HttpServletRequest request) {
		
		log.info("\n-------- requestBody : {}", requestBody.toString());
		
		// 기본정보
		int userId = (Integer) request.getAttribute("userId");
		int workoutId = 1;
		
		// quantity, tempo 추출
		int quantity;
		try {
			quantity = requestBody.get("quantity").asInt();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ResponseDto<>(400, "Missing or Mismatch key: **quantity**", null));
		}
		
		List<Double> tempoList = new ArrayList<>();
		try {
			tempoList = new ArrayList<>();
		    JsonNode tempoArray = requestBody.get("tempo");
		    for (JsonNode tempo : tempoArray) {
		        tempoList.add(tempo.asDouble());
		    }
		} catch (Exception e) {
			log.info("\n-------- No Tempo Data");
		}
		
		
		// workout detail 속성 만들기
		Map<String, Object> map = new HashMap<>();
		map.put("quantity", quantity);
		map.put("tempo", tempoList);

		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 1. RecordDto 완성
		RecordDto recordDto = new RecordDto(userId, workoutId, jsonString);
		
		Map<String, Object> workoutDetail;
		try {
			workoutDetail = objectMapper.readValue(recordDto.getWorkoutDetail(), new TypeReference<Map<String, Object>>() {});
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ResponseDto<>(400, "workoutDetail must be **JSON String**", null));
		}
		
		log.info("\n################ workoutDetail: {}", workoutDetail.toString());
		
		// 2. pushup 데이터 저장 (by RecordDto)
		recordService.addRecord(recordDto);
		
		// 3. 해당 유저의 consistency `last_workout_date` 업데이트
		consistencyService.updateLastWorkoutDate((Integer) request.getAttribute("userId"));
		
		// 4. return 성공
		return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto<>(200, "Pushup 기록 성공", null));
	} //-- pushup record API
	
	
	/**
	 * running을 기록하는 API
	 * @param requestBody
	 * @param request
	 * @return
	 */
	@PostMapping("/running")
	public ResponseEntity<?> recordRunning(@RequestBody JsonNode requestBody
			, HttpServletRequest request) {
		
		log.info("\n-------- requestBody : {}", requestBody.toString());
		
		// 기본정보
		int userId = (Integer) request.getAttribute("userId");
		int workoutId = 3;
		
		// duration, tempo
		double duration;
		try {
			duration = requestBody.get("duration").asDouble();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ResponseDto<>(400, "Missing or Mismatch key: **duration**", null));
		}
		
		List<Double> tempoList = new ArrayList<>();
		try {
			tempoList = new ArrayList<>();
		    JsonNode tempoArray = requestBody.get("tempo");
		    for (JsonNode tempo : tempoArray) {
		    	double value = tempo.asDouble();
		    	double truncated = Math.floor(value * 10) / 10.0;
		        tempoList.add(truncated);
		    }
		} catch (Exception e) {
			log.info("\n-------- No Tempo Data");
		}
		
		
		// workout detail 속성 만들기
		Map<String, Object> map = new HashMap<>();
		map.put("duration", duration);
		map.put("tempo", tempoList);

		String jsonString = null; // workout detail (map -> json)
		try {
			jsonString = objectMapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 1. RecordDto 완성
		RecordDto recordDto = new RecordDto(userId, workoutId, jsonString);
		
		Map<String, Object> workoutDetail;
		try {
			workoutDetail = objectMapper.readValue(recordDto.getWorkoutDetail(), new TypeReference<Map<String, Object>>() {});
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ResponseDto<>(400, "workoutDetail must be **JSON String**", null));
		}
		
		log.info("\n################ workoutDetail: {}", workoutDetail.toString());
		
		
		// 2. running 데이터 저장 (by RecordDto)
		recordService.addRecord(recordDto);
		
		// 3. 해당 유저의 consistency `last_workout_date` 업데이트
		consistencyService.updateLastWorkoutDate((Integer) request.getAttribute("userId"));
		
		// 4. return 성공
		return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto<>(200, "Running 기록 성공", null));
	} //-- running record API
}
