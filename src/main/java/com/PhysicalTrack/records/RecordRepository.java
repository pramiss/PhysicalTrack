package com.PhysicalTrack.records;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecordRepository extends JpaRepository<Record, Integer> {

	@Query("SELECT r FROM Record r WHERE r.workoutId = :workoutId AND r.createdAt >= :oneMonthAgo")
    List<Record> findPushupRecords(
    		@Param("workoutId") Integer workoutId,
    		@Param("oneMonthAgo") LocalDateTime oneMonthAgo);

}
