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
		
		// 변수 받기
		int userId = (Integer) request.getAttribute("userId");
		int workoutId = 1;
		
		// TODO 예외처리 -- 업뎃 후 삭제 예정
		if(requestBody.get("workoutDetail") != null) {
			log.info("\n-------- Dispatched attribute : workoutDetail");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ResponseDto<>(400, "Dispatched attribute : workoutDetail -- API 명세를 확인해주세요.", null));
		}
		
		
		
		// TODO 나중엔 quantity, tempo 따로 저장
		int quantity = requestBody.get("quantity").asInt();
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
		
		
		log.info("\n-------- tempoList : {}", tempoList);
		
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
		
		// 0. RecordDto 완성
		RecordDto recordDto = new RecordDto(userId, workoutId, jsonString);
		
		
		
		Map<String, Object> workoutDetail;
		try {
			workoutDetail = objectMapper.readValue(recordDto.getWorkoutDetail(), new TypeReference<Map<String, Object>>() {});
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ResponseDto<>(400, "workoutDetail must be **JSON String**", null));
		}
		
		log.info("\n################ workoutDetail: {}", workoutDetail.toString());
		
		/*
		
		// 1. pushup - Validation (필수: quantity, 선택: tempo)
		// pushup quanitity 검사: not null, type check
		Object quantityObj = workoutDetail.get("quantity");
		if (quantityObj == null) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		            .body(new ResponseDto<>(400, "Missing workoutDetail key: **quantity**", null));
		}
		if (!(quantityObj instanceof Number)) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		            .body(new ResponseDto<>(400, "Mismatch type workoutDetail key: **quantity**", null));
		}
		
		// pushpu tempo 검사: nullable, type check
		Object tempoObject = workoutDetail.get("tempo");
		try { // type casting 검사
			List<?> tempo = (List<?>) tempoObject;
			for (Object item : tempo) {
			    if (!(item instanceof Number)) {
			        throw new ClassCastException();
			    }
			}
		} catch (ClassCastException e) {
		    // 타입이 맞지 않을 경우 처리
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		            .body(new ResponseDto<>(400, "Mismatch type workoutDetail key: **tempo**", null));
		}
		
		*/
		
		// 2. pushup 데이터 저장 (by RecordDto)
		recordService.addRecord(recordDto);
		
		// 3. 해당 유저의 consistency `last_workout_date` 업데이트
		consistencyService.updateLastWorkoutDate((Integer) request.getAttribute("userId"));
		
		// 4. return 성공
		return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto<>(200, "Pushup 기록 성공", null));
	}
	
}
