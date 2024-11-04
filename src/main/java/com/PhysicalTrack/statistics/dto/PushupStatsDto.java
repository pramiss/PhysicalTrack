package com.PhysicalTrack.statistics.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class PushupStatsDto {
	private LocalDate date;
	private Integer quantity;
}
