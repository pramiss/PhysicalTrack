package com.PhysicalTrack.records;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.PhysicalTrack.records.dto.Record;
import com.PhysicalTrack.records.dto.RecordDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {

	private final RecordRepository recordRepository;
	
	/**
	 * Record 추가 - 운동 공통
	 * @param recordDto
	 */
	public void addRecord(RecordDto recordDto) {
		
		// 1. 기존 (created_at, workout_id) 가 존재하는지 확인 -- created_at은 today
		int userId = recordDto.getUserId();
		int workoutId = recordDto.getWorkoutId();
		LocalDate date = LocalDate.now(); // date는 today
	    
		Record recordEntity = recordRepository.findByUserIdAndWorkoutIdAndCreatedAtBetween(userId, workoutId, date);
		
		// 2. RecordEntity 완성
		if (recordEntity == null) {
			// 2-1. 없다면 -- 새로운 Record 생성, RecordDto -> Record
			recordEntity = Record.builder()
								.userId(recordDto.getUserId())
								.workoutId(recordDto.getWorkoutId())
								.workoutDetail(recordDto.getWorkoutDetail())
								.build();
		} else {
			// 2-2. 있다면 -- 기존 Record 업데이트
			recordEntity.setWorkoutDetail(recordDto.getWorkoutDetail());
		}
		
		// 2. Record 저장
		recordRepository.save(recordEntity);
	}
	
	/**
	 * (한달간의) WorkoutId Record 목록을 가져온다
	 * @param workoutId
	 * @param oneMonthAgo
	 * @return
	 */
	public List<Record> getMonthlyRecordsByWorkoutId(Integer workoutId, LocalDateTime oneMonthAgo) {
		return recordRepository.findMonthlyRecordsByWorkoutId(workoutId, oneMonthAgo);
	}
	
	/**
	 * (일주일간) Pushup Record 목록을 가져온다
	 * @param workoutId
	 * @param userId
	 * @param oneWeekAgo
	 * @return List<Record>
	 */
	public List<Record> getWeeklyPushupRecordsByWorkoutIduserId(Integer workoutId, Integer userId, LocalDateTime oneWeekAgo) {
		return recordRepository.findWeeklyPushupRecordsByWorkoutIduserId(workoutId, userId, oneWeekAgo);
	}
	
	/**
	 * (하루) Pushup Record를 가져온다
	 * @param userId
	 * @param date
	 * @return Record
	 */
	public Record getDailyPushupRecordByUserIdAndDate(Integer workoutId, Integer userId, LocalDate date) {
		return recordRepository.findDailyPushupRecordByUserIdDate(workoutId, userId, date);
	}
	
	/**
	 * 회원탈퇴 - 기록 삭제(전부) API
	 * @param userId
	 */
	public void deleteRecords(int userId) {
		recordRepository.deleteByUserId(userId);
	}
}
