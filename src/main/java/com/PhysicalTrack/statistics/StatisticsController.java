package com.PhysicalTrack.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PhysicalTrack.common.ResponseDto;
import com.PhysicalTrack.statistics.dto.PushupStatsDto;
import com.PhysicalTrack.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/statistics")
@RestController
public class StatisticsController {

	private final UserService userService;
	private final StatisticsService statisticsService;
	
	public StatisticsController(StatisticsService statisticsService, UserService userService) {
		this.userService = userService;
		this.statisticsService = statisticsService;
	}
	
	/**
	 * [통계조회 - Me] Get Weekly Pushup Stats API
	 * @param request
	 * @return
	 */
	@GetMapping("/weekly-stats/me")
	public ResponseEntity<?> weeklyStatsMe(HttpServletRequest request) {
		Map<String, Object> data = new HashMap<>();
		int userId = (int) request.getAttribute("userId");
		String name = (String) request.getAttribute("name");
		
		// 일주일간 data를 가져온다.
		List<PushupStatsDto> pushupStatsDtos;
		try {
			pushupStatsDtos = statisticsService.getWeeklyPushupStats(userId);
		} catch (JsonProcessingException e) {
			log.error("DB 확인 요망: JsonProcessingException {workoutDetail : quantity} in pushup");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
	                .body(new ResponseDto<>(422, "DB 확인 요망: JsonProcessingException {workoutDetail : quantity} in Pushup", null));
		}
		
		// data에 넣는다.
	    data.put("userId", userId);
	    data.put("name", name);
		data.put("pushupStats", pushupStatsDtos);
		
		// return
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto<>(200, "Weekly Stats : Me", data));
	}
	
	/**
	 * [통계조회 - Other] Get Weekly Pushup Stats API
	 * @param request
	 * @return
	 */
	@GetMapping("/weekly-stats/{userId}")
	public ResponseEntity<?> weeklyStatsOther(HttpServletRequest request, @PathVariable("userId") Integer userId) {
		Map<String, Object> data = new HashMap<>();
		String name = userService.getUserNameByUserId(userId);
		
		// 일주일간 data를 가져온다.
		List<PushupStatsDto> pushupStatsDtos;
		try {
			pushupStatsDtos = statisticsService.getWeeklyPushupStats(userId);
		} catch (JsonProcessingException e) {
			log.error("DB 확인 요망: JsonProcessingException {workoutDetail : quantity} in pushup");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
	                .body(new ResponseDto<>(422, "DB 확인 요망: JsonProcessingException {workoutDetail : quantity} in Pushup", null));
		}
		
		// data에 넣는다.
	    data.put("userId", userId);
	    data.put("name", name);
		data.put("pushupStats", pushupStatsDtos);
		
		// return
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto<>(200, "Weekly Stats : Me", data));
	}
}
