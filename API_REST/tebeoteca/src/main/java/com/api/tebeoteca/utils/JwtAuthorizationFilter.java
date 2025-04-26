package com.api.tebeoteca.utils;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private final String SECRET_KEY = "abc123.";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		/*final String authorizationHeader = request.getHeader("Authorization");
		String email = null;
		String jwt = null;
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			try {
				Claims claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(jwt).getBody();
				email = claims.getSubject();
			} catch (Exception e) {
				System.out.println("Token invalido: " + e.getMessage());
			}
		}
		filterChain.doFilter(request, response);*/
		
		/*String email = null;
		
		try {
			if (existeJWTToken(request, response)) {
				try {
					Claims claims = validarToken(request);
					email = claims.getSubject();
				} catch (Exception e) {
					System.out.println("Token inválido" + e.getMessage());
				}
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}*/
		
		
	}
	
	private boolean existeJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX)) {
			return false;
		}
		return true;
	}
	
	/*private Claims validarToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(jwtToken).getBody();
	}*/
	
	

}
