package com.PhysicalTrack.auth;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.PhysicalTrack.user.UserService;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	private final ModelMapper modelMapper;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;
	
	public AuthService(JwtTokenProvider jwtTokenProvider, UserService userService) {
		this.modelMapper = new ModelMapper();
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
	}
	
	// JWT Token 발급하기 (deviceId -> (UserDto) -> AccessToken)
	public String getToken(String deviceId) {
		AccessToken accessToken = modelMapper.map(userService.getUserByDeviceId(deviceId), AccessToken.class);
		return jwtTokenProvider.generateToken(accessToken);
	}
	
	// JWT Token 유효성 검사하기
	
	// JWT Token Claims 가져오기 (token -> Claims)
	public Claims getClaims(String token) {
		return jwtTokenProvider.getTokenClaims(token);
	}
	
}
