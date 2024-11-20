package com.PhysicalTrack.batch;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.PhysicalTrack.consistency.ConsistencyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@Component
public class BatchJob {

	private final ConsistencyService consistencyService;
	
	public BatchJob(ConsistencyService consistencyService) {
		this.consistencyService = consistencyService;
	}
	
	// update: CONSISTENCY `streak_count`
	// schedule: 매일 자정
    @Scheduled(cron = "0 0 0 * * *")
	public void updateConsistencyStreakCount() {
		LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul")).truncatedTo(ChronoUnit.SECONDS);
    	
    	log.info("-------- BatchJob: {} CONSISTENCY streak_count UPDATE starting... --------", today);
		consistencyService.updateStreakCount();
		log.info("-------- BatchJob: {} CONSISTENCY streak_count UPDATE complete..! --------", today);
	}
}
