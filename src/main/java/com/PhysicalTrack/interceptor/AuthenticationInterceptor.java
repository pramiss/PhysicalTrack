package com.PhysicalTrack.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.PhysicalTrack.auth.AuthService;
import com.PhysicalTrack.common.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
	
	private final ObjectMapper objectMapper;
	private final AuthService authService;
	
	public AuthenticationInterceptor(AuthService authService) {
		this.authService = authService;
		this.objectMapper = new ObjectMapper();
	}
	
	// PreHandler
	@Override
	public boolean preHandle(HttpServletRequest request
			, HttpServletResponse response, Object handler) throws Exception {
		
		// (0. 로그인/회원가입 api 은 예외)
		
		// 1. Request Header의 JWT Token 추출하기 - 예외) 토큰이 없는 경우
		String token = "";
		try {
			token = request.getHeader(HttpHeaders.AUTHORIZATION).replaceAll("Bearer ", "");
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            objectMapper.writeValue(response.getWriter(), new ResponseDto<>(401, "헤더에 토큰이 필요합니다. Authorization: Bear {jwt token}", null));
            return false;
		}
		
		
		// 2. JWT Token의 유효성 검사 by JwtTokenProvider - 예외) 유효기간 만료
		if (!authService.validateToken(token)) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.setContentType("application/json;charset=UTF-8");
            objectMapper.writeValue(response.getWriter(), new ResponseDto<>(406, "Expired JWT token", null));
			return false;
		}
		
		// 3. JWT Token Parsing 후 담아주기 - return true
		Claims claims = authService.getClaims(token);
		request.setAttribute("userId", claims.get("userId"));
		request.setAttribute("deviceId", claims.get("deviceId"));
		request.setAttribute("name", claims.get("name"));
		
		return true;
	}
	
}
