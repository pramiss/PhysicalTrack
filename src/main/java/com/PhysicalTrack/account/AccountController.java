package com.PhysicalTrack.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PhysicalTrack.account.dto.AccountDto;
import com.PhysicalTrack.common.ResponseDto;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AccountController {

	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	/**
	 * 회원 정보 불러오기 API
	 * @param request
	 * @return
	 */
	@GetMapping("/api/account")
	public ResponseEntity<?> getAccountInfo(HttpServletRequest request) {
		
		int userId = (Integer) request.getAttribute("userId");
		
		// user id로 회원정보 불러오기
		AccountDto accountDto = accountService.getAccountInfo(userId);
		
		log.info("\n####### accountDto : {}", accountDto);
		
		// 완료
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto<>(200, "회원 정보 불러오기 API 완료", accountDto));
	}
	
	/**
	 * 회원 정보 수정 API
	 * @param requestBody
	 * @param request
	 * @return
	 */
	@PutMapping("/api/account")
	public ResponseEntity<?> updateAccount(@RequestBody JsonNode requestBody, HttpServletRequest request) {
		
		log.info("\n-------- requestBody : {}", requestBody.toString());

		// Get fields.
		int userId = (Integer) request.getAttribute("userId");
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
		
		
		// AccountDto 구성
		AccountDto accountDto = AccountDto.builder()
										.userId(userId) // 필수
										.gender(gender) // 비필수
										.name(name) // 비필수
										.birthYear(birthYear) // 비필수
										.build();
		
		log.info("\n-------- accountDto : {}", accountDto.toString());
		
		// records 테이블에 있는 userId에 해당하는 데이터를 수정
		ResponseDto<?> responseDto = accountService.updateAccount(accountDto);
		
		// 완료
		if (responseDto.getStatus() != 200) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(responseDto);
		}
		
	}
	
	
	// 회원 탈퇴
	@DeleteMapping("/api/account")
	public ResponseEntity<?> deleteAccount(HttpServletRequest request) {
		
		// field
		int userId = (Integer) request.getAttribute("userId");
		
		// users, records, consistency 모두 삭제 -> Account Service 레이어에 위임
		accountService.deleteAccount(userId);
		
		// 삭제 후 jwt 처리에 대해서도 고민해야함.
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDto<>(200, "delete account api 완료", null));
	}
}
