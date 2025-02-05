package com.PhysicalTrack.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class RunningRankingDto extends RankingDto {

	private Double duration;
	
	public RunningRankingDto(int userId, double duration) {
		super(userId);
		this.duration = duration;
	}
}
