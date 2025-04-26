package com.api.tebeoteca.configs;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.tebeoteca.utils.JwtAuthorizationFilter;
import com.api.tebeoteca.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
	
	/*//Permite todo el acceso
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
		return http.build();
	}*/
	
	/*@Autowired
	private JwtAuthorizationFilter jwtAuthorizationFilter;*/
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/api/v1/auth/login").permitAll()
					.anyRequest().authenticated()
		)
		.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	public class JwtFilter extends OncePerRequestFilter {
		
		private JwtUtil jwtUtil;
		
		public JwtFilter(JwtUtil jwtUtil) {
			this.jwtUtil = jwtUtil;
		}

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
				FilterChain filterChain) throws ServletException, IOException {
			
			String authHeader = request.getHeader("Authorization");
			if(authHeader != null && authHeader.startsWith("Bearer ")) {
				String token = authHeader.substring(7);
				if(jwtUtil.validarToken(token)) {
					String username = jwtUtil.extractUsername(token);
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, null);
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
			filterChain.doFilter(request, response);
		}
	}
	
}
