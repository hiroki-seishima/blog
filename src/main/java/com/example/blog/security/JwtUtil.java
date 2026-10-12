package com.example.blog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
	private final SecretKey key;
	private final long expirationMs;
	
	public JwtUtil(@Value("${jwt.secret}")String secret, @Value("${Jwt,expiration-ms}")long expirationMs) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationMs = expirationMs;
	}
	
	//トークンを発行する
	public String generateToken(String username, String role) {
		Date now = new Date();
		return Jwts.builder()
				.subject(username)
				.claim("role", role)
				.issuedAt(now)
				.expiration(new Date(now.getTime() + expirationMs))
				.signWith(key)
				.compact();
	}
	//トークンからユーザー名を取り出す
	public String getUsername(String token) {
		return parseClaims(token).get("role", String.class);
	}
	
	//トークンから権限（role）を取り出す
	public String getRole(String token){
		return parseClaims(token).get("role", String.class);
	}
	
	//トークンが正しいか（改ざんなし：期限切れでない）を調べる
	public boolean isValid(String token) {
		try {
			parseClaims(token);
			return true;
		}catch (JwtException | IllegalArgumentException e) {
			return false;
		}	
	}
	private Claims parseClaims(String token) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();

}
