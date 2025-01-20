package com.PhysicalTrack.account;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PhysicalTrack.account.dto.AccountDto;
import com.PhysicalTrack.common.ResponseDto;
import com.PhysicalTrack.consistency.ConsistencyService;
import com.PhysicalTrack.records.RecordService;
import com.PhysicalTrack.user.UserService;
import com.PhysicalTrack.user.dto.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

	public final UserService userService;
	public final RecordService recordService;
	public final ConsistencyService consistencyService;
	
	
	/**
	 * 회원정보 불러오기 API - userService
	 * @param userId
	 * @return
	 */
	public AccountDto getAccountInfo(int userId) {
		
		// User Service에서 유저정보 가져오기
		UserDto userDto = userService.getUserById(userId);
		
		// UserDto -> AccountDto 변환
		AccountDto accountDto = AccountDto.builder()
										.userId(userId)
										.gender(userDto.getGender())
										.name(userDto.getName())
										.birthYear(userDto.getBirthYear())
										.build();
		
		// 반환
		return accountDto;
	}
	
	/**
	 * 회원정보수정 API - userService
	 * @param accountDto
	 * @return
	 */
	@Transactional
	public ResponseDto<?> updateAccount(AccountDto accountDto) {
		// Validation.
		String name = accountDto.getName();
		int birthYear = accountDto.getBirthYear();
		String gender = accountDto.getGender();
		
		if (name.length() > 64) {
			return new ResponseDto<>(400, "이름이 64자리를 초과합니다.", null);
		}
		if (birthYear < 1900 || birthYear > 2100) {
			return new ResponseDto<>(400, "출생년도 범위오류: {1900 < birthYear < 2100}", null);
		}
		if (!List.of("male", "female").contains(gender)) {
			return new ResponseDto<>(400, "성별은 (male/female)만 가능합니다.", null);
		}
		
		// update user by UserService
		userService.updateUser(accountDto);
		
		return new ResponseDto<>(200, "update account api 완료", null);
	}
	
	/**
	 * 회원탈퇴 API - userService, recordService, consistencyService
	 * @param userId
	 */
	@Transactional
	public void deleteAccount(int userId) {
		
		// users 삭제 - UserService
		log.info("\n-------- userService.deleteUser start");
		userService.deleteUser(userId);
		
		// records 삭제 - RecordService
		log.info("\n-------- recordService.deleteUser start");
		recordService.deleteRecords(userId);
		
		// consistency 삭제 - ConsistencyService
		log.info("\n-------- consistencyService.deleteUser start");
		consistencyService.deleteConsistency(userId);
		
		// 완료
		log.info("\n-------- All completed.");
	}
	
	
}
