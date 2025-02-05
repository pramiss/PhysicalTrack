package com.PhysicalTrack.ranking;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PhysicalTrack.common.ResponseDto;
import com.PhysicalTrack.ranking.dto.ConsistencyRankingDto;
import com.PhysicalTrack.ranking.dto.PushupRankingDto;
import com.PhysicalTrack.ranking.dto.RunningRankingDto;
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
	 * Pushup Ranking API
	 * @param request
	 * @return
	 */
	@GetMapping("/pushup")
	public ResponseEntity<?> getPushupRanking(HttpServletRequest request) {
		
		// 0. data field
		List<PushupRankingDto> pushupRankingList = null;

		// 1. pushup ranking 조회
		try {
			pushupRankingList = rankingService.getMonthlyPushupRanking();
		} catch (JsonProcessingException e) {
			log.error("DB 확인 요망: JsonProcessingException {workoutDetail : quantity} in pushup");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
	                .body(new ResponseDto<>(422, "DB 확인 요망: JsonProcessingException {workoutDetail : quantity} in Pushup", null));
		}
		
		// return 200
		return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto<>(200, "Pushup Raking 조회 성공", pushupRankingList));
	}
	
	// Running Ranking API
	@GetMapping("/running")
	public ResponseEntity<?> getRunningRanking(HttpServletRequest request) {
		
		// 0. data field
		List<RunningRankingDto> runningRankingList = null;

		// 1. running ranking 조회
		try {
			runningRankingList = rankingService.getMonthlyRunningRanking();
		} catch (JsonProcessingException e) {
			log.error("DB 확인 요망: JsonProcessingException {workoutDetail : duration} in running");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
	                .body(new ResponseDto<>(422, "DB 확인 요망: JsonProcessingException {workoutDetail : duration} in Running", null));
		}
		
		// return 200
		return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto<>(200, "Running Raking 조회 성공", runningRankingList));
	}
	
	// Consistency Ranking API
	@GetMapping("/consistency")
	public ResponseEntity<?> getConsistencyRanking(HttpServletRequest request) {
		
		// 0. data field
		List<ConsistencyRankingDto> consistencyRankingList = null;

		// 1. consistency ranking 조회
		consistencyRankingList = rankingService.getConsistencyRanking();
		
		// return 200
		return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto<>(200, "Consistency Raking 조회 성공", consistencyRankingList));
	}
}
