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
	private Integer userId; // token에서 받기
	private Integer workoutId; // controller에서 설정
	private String workoutDetail; // frontend에서 받은 것 취합해서 JSON 저장
}
