package com.PhysicalTrack.ranking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PhysicalTrack.common.ResponseDto;
import com.PhysicalTrack.ranking.dto.PushupRankingDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/ranking")
@RestController
public class RankingController {
	
	private final RankingService rankingService;
	
	public RankingController(RankingService rankingService) {
		this.rankingService = rankingService;
	}
	
	/**
	 * Pushup Ranking 가져오는 API
	 * @param request
	 * @return
	 */
	@GetMapping("/pushup")
	public ResponseEntity<?> getPushupRanking(HttpServletRequest request) {
		
		// 0. data field
		Map<String, Object> data = new HashMap<>();
		List<PushupRankingDto> pushupRankingList = null;

		// 1. pushup ranking 조회
		try {
			pushupRankingList = rankingService.getMonthlyPushupRanking();
		} catch (JsonProcessingException e) {
			log.error("DB 확인 요망: JsonProcessingException {workoutDetail : quantity} in pushup");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
	                .body(new ResponseDto<>(422, "DB 확인 요망: JsonProcessingException {workoutDetail : quantity} in Pushup", null));
		}
		
		// return.
		data.put("pushupRanking", pushupRankingList);
		return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto<>(200, "Pushup Raking 조회 성공", data));
	}
}
