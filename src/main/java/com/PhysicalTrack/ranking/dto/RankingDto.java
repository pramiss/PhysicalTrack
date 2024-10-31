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
@ToString
public abstract class RankingDto {
	private Integer userId;
	private String name;
	private Integer rank;
	
	public RankingDto(int userId) {
		this.userId = userId;
	}
}
