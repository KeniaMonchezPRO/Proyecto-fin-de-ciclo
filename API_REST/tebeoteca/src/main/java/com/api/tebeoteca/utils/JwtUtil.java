package com.api.tebeoteca.utils;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final String SECRET_KEY = "abc123.asdasdsad_qowieuqwoijjdjdjd_564444.ieeeewwwwwww";
	private final long tiempoExpiracion = 30000;
	
	public String generarToken(String username) {
		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + tiempoExpiracion))
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
			.compact();
	}
	
	public boolean validarToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token);
			return true;
		} catch(JwtException | IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String extractUsername(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	/*private final String SECRET_KEY = "abc123.asdasdsad_qowieuqwoijjdjdjd_564444.ieeeewwwwwww";
	private final long LIMIT = 30000;
	
	public String generarToken(String email) {
		return Jwts.builder()
			.setSubject(email)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + LIMIT))
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
			.compact();
	}*/
}
