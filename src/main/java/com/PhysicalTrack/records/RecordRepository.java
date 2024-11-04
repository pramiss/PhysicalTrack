package com.PhysicalTrack.records;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.PhysicalTrack.records.dto.Record;

public interface RecordRepository extends JpaRepository<Record, Integer> {

	@Query("SELECT r FROM Record r WHERE r.workoutId = :workoutId AND r.createdAt >= :oneMonthAgo")
    List<Record> findMonthlyPushupRecordsByWorkoutId(
    		@Param("workoutId") Integer workoutId,
    		@Param("oneMonthAgo") LocalDateTime oneMonthAgo);
	
	@Query("SELECT r FROM Record r WHERE r.workoutId = :workoutId AND r.userId = :userId AND r.createdAt >= :oneWeekAgo ORDER BY r.createdAt ASC")
	List<Record> findWeeklyPushupRecordsByWorkoutIduserId(
			@Param("workoutId") Integer workoutId, 
			@Param("userId") Integer userId, 
			@Param("oneWeekAgo") LocalDateTime oneWeekAgo);

}
