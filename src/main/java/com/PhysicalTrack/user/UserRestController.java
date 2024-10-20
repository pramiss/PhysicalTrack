package com.PhysicalTrack.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PhysicalTrack.common.responseDto.BasicResponseDto;
import com.PhysicalTrack.common.responseDto.DataResponseDto;
import com.PhysicalTrack.common.responseDto.ResponseDto;
import com.PhysicalTrack.user.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/user")
@RestController
public class UserRestController {

	private UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 유저 등록 API
	 * @param UserDto
	 * @return
	 */
	@PostMapping("/sign-up")
	public ResponseDto signUp(@RequestBody UserDto userDto) {
		
		log.info("##### userDto : " + userDto.toString());
		
		// 1-1. 입력 데이터 유효성 검사
		if (userDto.getDeviceId().length() > 64) {
			return new BasicResponseDto(400, "Device Id가 64자리를 초과합니다.");
		}
		if (userDto.getName().length() > 64) {
			return new BasicResponseDto(400, "이름이 64자리를 초과합니다.");
		}
		if (userDto.getAge() > 200 || userDto.getAge() < 1) {
			return new BasicResponseDto(400, "나이는 200이하의 자연수만 입력가능합니다.");
		}
		if (!List.of("male", "female").contains(userDto.getGender().toLowerCase())) {
			return new BasicResponseDto(400, "성별은 (male/female)만 가능합니다.");
		}
		
		// 1-2. 이미 등록 되었는지 확인
		if (userService.getUserByDeviceId(userDto.getDeviceId()) != null) {
			return new BasicResponseDto(401, "이미 등록된 deviceId입니다.");
		}
		
		// 2. 사용자 정보 저장
		userService.addUser(userDto);
		
		// 3. TODO: 토큰 생성 by deviceId
		String token = null;
		
        // result(성공)
		return new DataResponseDto<String>(200, "사용자 등록 성공", token);
	}
	
	/**
	 * 유저 조회 API
	 * @param userDto
	 * @return
	 */
	@PostMapping("/sign-in")
	public ResponseDto signIn(@RequestBody UserDto userDto) {
		
		log.info("##### userDto : " + userDto.toString());
		
		// 1. deviceId에 해당하는 유저가 있는지 user db에서 확인
		String deviceId = userDto.getDeviceId();
		userDto = userService.getUserByDeviceId(deviceId);
		
		// 2-1. 없다면 에러메시지 리턴 후 종료
		if (userDto == null) {
			return new BasicResponseDto(401, "존재하지 않는 deviceId입니다.");
		}
		
		// 2-2. TODO: 있다면 jwt token 생성
		
		// 3. 성공 메시지와 토큰을 반환
		log.info("##### userDto : " + userDto.toString());
		return new DataResponseDto<String>(200, "유저 확인 성공", "token");
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
