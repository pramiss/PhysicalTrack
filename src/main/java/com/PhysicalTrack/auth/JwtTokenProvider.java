package com.PhysicalTrack.auth;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
	
	// Token 생성하기
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
	
	// Token 가져오기
	public Claims getTokenClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
}
