package com.PhysicalTrack.records;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.PhysicalTrack.records.dto.Record;

public interface RecordRepository extends JpaRepository<Record, Integer> {

	// (한달간의) PushUp Record 목록을 가져온다
	@Query("SELECT r FROM Record r WHERE r.workoutId = :workoutId AND r.createdAt >= :oneMonthAgo")
    List<Record> findMonthlyPushupRecordsByWorkoutId(
    		@Param("workoutId") Integer workoutId,
    		@Param("oneMonthAgo") LocalDateTime oneMonthAgo);

	// (일주일간) Pushup Record 목록을 가져온다
	@Query("SELECT r FROM Record r WHERE r.workoutId = :workoutId AND r.userId = :userId AND r.createdAt >= :oneWeekAgo ORDER BY r.createdAt ASC")
	List<Record> findWeeklyPushupRecordsByWorkoutIduserId(
			@Param("workoutId") Integer workoutId, 
			@Param("userId") Integer userId, 
			@Param("oneWeekAgo") LocalDateTime oneWeekAgo);

	// (하루) Pushup Record를 가져온다 - 가장 최근 기록
	@Query(value = "SELECT * FROM records WHERE workout_id = :workoutId AND user_id = :userId AND DATE(created_at) = :date ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
	Record findDailyPushupRecordByUserIdDate(
			@Param("workoutId") Integer workoutId,
			@Param("userId") Integer userId,
			@Param("date") LocalDate date);
}
