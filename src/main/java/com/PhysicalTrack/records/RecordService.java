package com.PhysicalTrack.records;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordService {

	private final RecordRepository recordRepository;
	
	// Record 추가 - 운동 공통
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
}
