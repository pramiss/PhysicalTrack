package com.PhysicalTrack.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

	private UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	// 테스트 (유저 조회)
	@GetMapping("/api/user/test1")
	public String test1() {
		return "Hello, world!";
	}
	@GetMapping("/api/user/test2")
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "pramiss");
		map.put("age", 27);
		map.put("gender", "male");
		return map;
	}
	@GetMapping("/api/user/test3")
	public List<User> test3() {
		return userService.test3();
	}
	
	// 회원가입
//	@PostMapping("/api/user/sign-up")
	
	// 로그인
//	@PostMapping("/api/user/sign-in")
}
