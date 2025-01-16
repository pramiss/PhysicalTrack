package com.PhysicalTrack.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class AccountDto {
	private int userId; // 타켓 튜플의 회원 ID, Not null
	private String gender; // 수정될 성별
	private String name; // 수정될 이름
	private int birthYear; // 수정될 출생년도
}
