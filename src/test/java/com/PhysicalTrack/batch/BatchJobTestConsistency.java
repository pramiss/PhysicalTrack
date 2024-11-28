package com.PhysicalTrack.batch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BatchJobTestConsistency {
	
	@Autowired
	private BatchJob batchJob;
	
	@Test
	public void batchJobTest() {
		batchJob.updateConsistencyStreakCount();
	}

}
