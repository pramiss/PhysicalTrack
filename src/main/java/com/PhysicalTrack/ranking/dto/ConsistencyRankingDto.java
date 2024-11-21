package com.PhysicalTrack.ranking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class ConsistencyRankingDto extends RankingDto{
	private Integer streakCount;
	
	public ConsistencyRankingDto(Integer userId, Integer streakCount) {
		super(userId);
		this.streakCount = streakCount;
	}
}
