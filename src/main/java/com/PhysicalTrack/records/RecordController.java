package com.PhysicalTrack.records;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PhysicalTrack.common.ResponseDto;
import com.PhysicalTrack.records.dto.RecordDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/record/")
@RestController
public class RecordController {

	private final ObjectMapper objectMapper;
	private final RecordService recordService;
	
	public RecordController(RecordService recordService) {
		this.recordService = recordService;
		this.objectMapper = new ObjectMapper();
	}
	
	/**
	 * pushup을 기록하는 API
	 * @param recordDto
	 * @param request
	 * @return
	 */
	@PostMapping("/pushups")
	public ResponseEntity<?> recordPushup(@RequestBody RecordDto recordDto
			, HttpServletRequest request) {
		// 0. RecordDto 완성
		recordDto.setUserId((Integer) request.getAttribute("userId"));
		
		// 1. pushup - Validation (필수: quantity, 선택: tempo)
		Map<String, Object> workoutDetail;
		try {
			workoutDetail = objectMapper.readValue(recordDto.getWorkoutDetail(), new TypeReference<Map<String, Object>>() {});
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ResponseDto<>(400, "workoutDetail must be **JSON String**", null));
		}
		
		Object quantityObj = workoutDetail.get("quantity");
		if (quantityObj == null) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		            .body(new ResponseDto<>(400, "Missing workoutDetail key: **quantity**", null));
		}
		if (!(quantityObj instanceof Number)) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		            .body(new ResponseDto<>(400, "Mismatch type workoutDetail key: **quantity**", null));
		}
		
		// 2. pushup 데이터 저장 (by RecordDto)
		recordService.addRecord(recordDto);
		
		// 3. return 성공
		return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto<>(200, "Pushup 기록 성공", null));
	}
	
}
