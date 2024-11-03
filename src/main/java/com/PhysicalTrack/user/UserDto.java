package com.PhysicalTrack.user;

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
public class UserDto {
	private Integer userId;
	private String deviceId;
	private String name;
	private String gender;
	private Integer birthYear;
}
