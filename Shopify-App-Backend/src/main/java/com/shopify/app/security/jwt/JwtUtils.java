package com.shopify.app.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.shopify.app.service.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${mali.app.jwtSecret}")
	private String jwtSecret;

	@Value("${mali.app.jwtExpirationMs}")
	private long jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		Date now = new Date();
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + jwtExpirationMs)).signWith(key).compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build()
				.parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build()
					.parseClaimsJws(authToken);
			Date expiration = claims.getBody().getExpiration();
			if (expiration.before(new Date())) {
				logger.error("JWT token has already expired.");
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
			return false;
		}
	}
}
