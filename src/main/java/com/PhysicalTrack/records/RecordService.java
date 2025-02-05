package com.PhysicalTrack.records;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.PhysicalTrack.records.dto.Record;
import com.PhysicalTrack.records.dto.RecordDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordService {

	private final RecordRepository recordRepository;
	
	/**
	 * Record 추가 - 운동 공통
	 * @param recordDto
	 */
	public void addRecord(RecordDto recordDto) {
		// 1. RecordDto -> Record
		Record recordEntity = Record.builder()
									.userId(recordDto.getUserId())
									.workoutId(recordDto.getWorkoutId())
									.workoutDetail(recordDto.getWorkoutDetail())
									.build();
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
