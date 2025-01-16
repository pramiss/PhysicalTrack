package com.PhysicalTrack.account;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PhysicalTrack.account.dto.AccountDto;
import com.PhysicalTrack.common.ResponseDto;
import com.PhysicalTrack.user.UserService;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/account")
@RestController
public class AccountController {

	private final UserService userService;
	
	public AccountController(UserService userService) {
		this.userService = userService;
	}
	
	// 회원 정보 수정 API
	@PutMapping("/update")
	public ResponseEntity<?> updateAccount(@RequestBody JsonNode requestBody, HttpServletRequest request) {
		
		log.info("\n-------- requestBody : {}", requestBody.toString()); // ok

		// Get fields.
		int userId = (Integer) request.getAttribute("userId"); // ok
		String name;
		String gender;
		int birthYear;
		
		try {
			name = requestBody.get("name").asText();
			gender = requestBody.get("gender").asText();
			birthYear = requestBody.get("birthYear").asInt();
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseDto<>(400, "name, gender, birthYear 데이터를 모두 보내주세요.", null));
		}
		
		// Validation.
		if (name.length() > 64) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(400, "이름이 64자리를 초과합니다.", null));
		}
		if (birthYear < 1900 || birthYear > 2100) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(400, "출생년도 범위오류: {1900 < birthYear < 2100}", null));
		}
		if (!List.of("male", "female").contains(gender)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(400, "성별은 (male/female)만 가능합니다.", null));
		}
		
		// AccountDto 구성
		AccountDto accountDto = AccountDto.builder()
										.userId(userId) // 필수
										.gender(gender) // 비필수
										.name(name) // 비필수
										.birthYear(birthYear) // 비필수
										.build();
		
		log.info("\n-------- accountDto : {}", accountDto.toString());
		
		// records 테이블에 있는 userId에 해당하는 데이터를 수정
		userService.updateUser(accountDto);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto<>(200, "update account api", null));
	}
	
	
	// 회원 탈퇴
//	@DeleteMapping("/delete")
//	public ResponseEntity<?> deleteAccount() {
//		// users, records, consistency 모두 삭제
//	}
}
