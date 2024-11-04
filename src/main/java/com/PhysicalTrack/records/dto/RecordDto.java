package com.PhysicalTrack.records.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RecordDto {
	private Integer userId;
	private Integer workoutId;
	private String workoutDetail;
}
