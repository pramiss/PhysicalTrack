package com.PhysicalTrack.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.PhysicalTrack.auth.AuthService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
	
	private final AuthService authService;
	
	
	// PreHandler
	@Override
	public boolean preHandle(HttpServletRequest request
			, HttpServletResponse response, Object handler) throws Exception {
		
		// (0. 로그인/회원가입 api 은 예외)
		
		// 1. Request Header의 JWT Token 추출하기 - 예외) 토큰이 없는 경우
		String token = request.getHeader(HttpHeaders.AUTHORIZATION).replaceAll("Bearer ", "");
		
		// 2. TODO: JWT Token의 유효성 검사 by JwtTokenProvider - 예외) 유효기간 만료
		
		// 3. JWT Token Parsing 후 담아주기 - return true
		Claims claims = authService.getClaims(token);
		request.setAttribute("userId", claims.get("userId"));
		request.setAttribute("deviceId", claims.get("deviceId"));
		request.setAttribute("name", claims.get("name"));
		
		return true;
	}
	
}
