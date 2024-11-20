package com.PhysicalTrack.consistency;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.PhysicalTrack.consistency.dto.Consistency;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsistencyService {
	
	private final ConsistencyRepository consistencyRepository;
	
	public ConsistencyService(ConsistencyRepository consistencyRepository) {
		this.consistencyRepository = consistencyRepository;
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
	
	// 모든유저 'streak_count' 업데이트
	public void updateStreakCount() {
		
		// field: 오늘날짜
		LocalDate baseline = LocalDate.now(ZoneId.of("Asia/Seoul"));
		
		// `consistency`의 모든 튜플들을 불러온다.
		List<Consistency> consistencyList = consistencyRepository.findAll();
		
		for (Consistency consistency : consistencyList) {
			// 튜플의 'last_workout_date' 항목과 baseline을 비교
			boolean flag = consistency.getLastWorkoutDate().equals(baseline);
			int streakCount = consistency.getStreakCount();
			
			// 일치한다면 'streak_count' + 1
			if (flag) {
				streakCount++;
			}
			// 일치하지 않는다면 reset: 0
			else {
				streakCount = 0;
			}
			
			// streak_count 업데이트
			consistency.setStreakCount(streakCount);
			consistencyRepository.save(consistency);
		} //-- for loop
	} //-- updateStreakCount
} //-- ConsistencyService
