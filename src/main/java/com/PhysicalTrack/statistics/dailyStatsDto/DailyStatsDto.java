package com.PhysicalTrack.statistics.dailyStatsDto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class DailyStatsDto {
	private Integer userId;
	private String name;
	private LocalDate date;
	private List<Double> pushupTempo;
	// TODO: private List<Double> situpTempo;
	// TODO: private List<Double> runningTempo;
}
