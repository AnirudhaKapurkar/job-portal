package com.jobportal.job_portal.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

import com.jobportal.job_portal.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private long expiration;
	
	
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
	private String generateToken(User user) {
		return Jwts.builder()
				.subject(user.getEmail())
				.claim("role", user.getRole().name())
				.claim("fullName",user.getFullName())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(getSigningKey())
				.compact();		
	}
	 public String extractEmail(String token) {
	        return getClaims(token).getSubject();
	    }

	    public String extractRole(String token) {
	        return getClaims(token).get("role", String.class);
	    }

	    public boolean isTokenValid(String token) {
	        try {
	            Claims claims = getClaims(token);
	            return claims.getExpiration().after(new Date());
	        } catch (Exception e) {
	            return false;
	        }
	    }

	    private Claims getClaims(String token) {
	        return Jwts.parser()
	                .verifyWith(getSigningKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	    }
		
}

