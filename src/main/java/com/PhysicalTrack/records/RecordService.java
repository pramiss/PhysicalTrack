package com.PhysicalTrack.records;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

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
	 * (한달간의) PushUp Record 목록을 가져온다
	 * @param workoutId
	 * @param oneMonthAgo
	 * @return
	 */
	public List<Record> getPushupRecords(Integer workoutId, LocalDateTime oneMonthAgo) {
		return recordRepository.findPushupRecords(workoutId, oneMonthAgo);
	}
}
