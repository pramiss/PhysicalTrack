package com.PhysicalTrack.common.responseDto;

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
public class DataResponseDto<T> implements ResponseDto {
	
	private int status;
	private String message;
	private T data;
	
}
