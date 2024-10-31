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
public class PushupRankingDto extends RankingDto {
	private Integer quantity;
	
	public PushupRankingDto(int userId, int quantity) {
		super(userId);
		this.quantity = quantity;
	}
}
