package com.PhysicalTrack.statistics;

import java.time.LocalDate;
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
import com.PhysicalTrack.statistics.dailyStatsDto.DailyStatsDto;
import com.PhysicalTrack.statistics.weeklyStatsDto.PushupStatsDto;
import com.PhysicalTrack.statistics.weeklyStatsDto.RunningStatsDto;
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
	
	/** (폐기)
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
				.body(new ResponseDto<>(200, "Weekly Stats API 조회 성공", data));
	}
	
	/**
	 * [통계조회] Get Weekly Stats API
	 * @param request
	 * @return
	 */
	@GetMapping("/weekly-stats/{userId}")
	public ResponseEntity<?> weeklyStatsOther(HttpServletRequest request, @PathVariable("userId") Integer userId) {
		Map<String, Object> data = new HashMap<>();
		String name = userService.getUserNameByUserId(userId);
		
		// 1. GET: 일주일간 pushup data
		List<PushupStatsDto> pushupStatsDtos;
		try {
			pushupStatsDtos = statisticsService.getWeeklyPushupStats(userId);
		} catch (JsonProcessingException e) {
			log.error("RECORDS DB 확인 요망: JsonProcessingException {workoutDetail} in pushup");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
	                .body(new ResponseDto<>(422, "RECORDS DB 확인 요망: JsonProcessingException {workoutDetail} in Pushup", null));
		}
		
		// 2. GET: 일주일간 running data
		List<RunningStatsDto> runningStatsDtos;
		try {
			runningStatsDtos = statisticsService.getWeeklyRunningStats(userId);
		} catch (JsonProcessingException e) {
			log.error("RECORDS DB 확인 요망: JsonProcessingException {workoutDetail} in running");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
	                .body(new ResponseDto<>(422, "RECORDS DB 확인 요망: JsonProcessingException {workoutDetail} in Running", null));
		}
		
		
		
		// TODO: 3. GET: 일주일간 situp data
		
		// data input
	    data.put("userId", userId);
	    data.put("name", name);
		data.put("pushupStats", pushupStatsDtos);
		data.put("runningStats", runningStatsDtos);
		
		// return
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto<>(200, "Weekly Stats API 조회 성공", data));
	}
	
	/**
	 * [통계조회] Get Daily Stats API
	 * @param request
	 * @param userId
	 * @param date
	 * @return
	 */
	@GetMapping("/daily-stats/{userId}/{date}")
	public ResponseEntity<?> weeklyStatsOther(HttpServletRequest request, 
			@PathVariable("userId") Integer userId, @PathVariable("date") LocalDate date) {
		
		String name = userService.getUserNameByUserId(userId);
		int workoutId;
		
		// 1. GET: pushup tempo 
		List<Double> pushupTempo = null;
		try {
			workoutId = 1;
			pushupTempo = statisticsService.getDailyWorkoutTempo(workoutId, userId, date);
		} catch (JsonProcessingException e) {
			log.error("DB 확인 요망: JsonProcessingException {workoutDetail : tempo} in pushup");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
	                .body(new ResponseDto<>(422, "DB 확인 요망: JsonProcessingException {workoutDetail : tempo} in Pushup", null));
		}
		
		// TODO: 2. GET: 일주일간 running tempo
		List<Double> runningTempo = null;
		try {
			workoutId = 3;
			runningTempo = statisticsService.getDailyWorkoutTempo(workoutId, userId, date);
		} catch (JsonProcessingException e) {
			log.error("DB 확인 요망: JsonProcessingException {workoutDetail : tempo} in running");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
	                .body(new ResponseDto<>(422, "DB 확인 요망: JsonProcessingException {workoutDetail : tempo} in Running", null));
		}
		
		
		// TODO: 3. GET: 일주일간 situp tempo
		
		// DailyStatsDto input
	    DailyStatsDto dailyStatsDto = DailyStatsDto.builder()
	    										.userId(userId)
	    										.name(name)
	    										.date(date)
	    										.pushupTempo(pushupTempo)
	    										.runningTempo(runningTempo)
	    										.build();
		
		// return
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto<>(200, "Daily Stats API 조회 성공", dailyStatsDto));
	}
}
