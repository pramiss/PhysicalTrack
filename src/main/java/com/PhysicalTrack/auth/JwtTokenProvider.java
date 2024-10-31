package com.PhysicalTrack.auth;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtTokenProvider {
	
	@Value("${jwt.secret}")
	private String JWT_SECRET;
	private Key key;
	
	@Value("${jwt.expiration}")
	private long JWT_EXPIRATION_MS;
	
	
    // Key 생성 - bean 생성 시 자동 호출
    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        key = Keys.hmacShaKeyFor(keyBytes);
    }
	
	/**
	 * JWT Token 생성하기
	 * @param tokenRequest
	 * @return
	 */
	public String generateToken(AccessToken tokenRequest) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + JWT_EXPIRATION_MS);
		
		return Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.claim("deviceId", tokenRequest.getDeviceId())
				.claim("userId", tokenRequest.getUserId())
				.claim("name", tokenRequest.getName())
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	/**
	 * JWT Token 유효성 검증
	 * @param token
	 * @return
	 */
	public boolean validateToken(String token) {
		
		try {
			// Token Parsing을 통한 검증
			Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
			return true;
 		} catch (SecurityException | MalformedJwtException e) {
 	        log.error("잘못된 JWT 서명입니다.");
 	    } catch (ExpiredJwtException e) {
 	        log.error("만료된 JWT 토큰입니다.");
 	    } catch (UnsupportedJwtException e) {
 	        log.error("지원되지 않는 JWT 토큰입니다.");
 	    } catch (IllegalArgumentException e) {
 	        log.error("JWT 토큰이 잘못되었습니다.");
 	    }
		
		return false;
	}
	
	
	/**
	 * JWT Token 가져오기
	 * @param token
	 * @return
	 */
	public Claims getTokenClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
}
