package com.PhysicalTrack.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PhysicalTrack.auth.AuthService;
import com.PhysicalTrack.common.ResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final AuthService authService;
	
	
	/**
	 * 유저 등록 API
	 * @param UserDto
	 * @return
	 */
	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
		
		// 0. fields
		String deviceId = userDto.getDeviceId();
		String name = userDto.getName();
		int age = userDto.getAge();
		String gender = userDto.getGender();
		
		// 1-1. 입력 데이터 유효성 검사
		if (deviceId.length() > 64) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(400, "Device Id가 64자리를 초과합니다.", null));
		}
		if (name.length() > 64) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(400, "이름이 64자리를 초과합니다.", null));
		}
		if (age > 200 || age < 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(400, "나이는 200이하의 자연수만 입력가능합니다.", null));
		}
		if (!List.of("male", "female").contains(gender)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(400, "성별은 (male/female)만 가능합니다.", null));
		}
		
		// 1-2. 이미 등록 되었는지 확인
		if (userService.getUserByDeviceId(deviceId) != null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDto<>(401, "이미 등록된 deviceId입니다.", null));
		}
		
		// 2. 사용자 정보 저장
		userService.addUser(userDto);
		
		// 3. 토큰 생성 by deviceId
		String token = authService.getToken(deviceId);
		
        // result(성공)
		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(new ResponseDto<>(200, "사용자 등록 성공", null));
	}

	/**
	 * 유저 조회 API
	 * @param userDto
	 * @return
	 */
	@PostMapping("/sign-in")
	public ResponseEntity<?> signIn(@RequestBody UserDto userDto) {
		
		// 1. deviceId에 해당하는 유저가 있는지 user db에서 확인
		String deviceId = userDto.getDeviceId();
		userDto = userService.getUserByDeviceId(deviceId);
		
		// 1-2. 없다면 에러메시지 리턴 후 종료
		if (userDto == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(new ResponseDto<>(401, "존재하지 않는 deviceId입니다.", null));
		}
		
		// 2. JWT token 생성(by deviceId)
		String token = authService.getToken(deviceId);
		
		// 3. 성공 메시지와 토큰을 반환
		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(new ResponseDto<>(200, "유저 확인 성공", null));
	}
	
	
	
	

	// 테스트 (유저 조회)
	@GetMapping("/test1")
	public String test1() {
		return "Hello, world!";
	}
	@GetMapping("/test2")
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "pramiss");
		map.put("age", 27);
		map.put("gender", "male");
		return map;
	}
	@GetMapping("/test3")
	public List<User> test3() {
		return userService.test3();
	}
	
}
