package com.jobportal.job_portal.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobportal.job_portal.dto.AuthResponse;
import com.jobportal.job_portal.dto.LoginRequest;
import com.jobportal.job_portal.dto.RegisterRequest;
import com.jobportal.job_portal.entity.User;
import com.jobportal.job_portal.repository.UserRepository;
import com.jobportal.job_portal.security.JwtUtil;

@Service
public class AuthService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final AuthenticationManager authenticationManager;
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
			AuthenticationManager authenticationManager) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
	}
	
	public AuthResponse register(RegisterRequest request) {
		if(userRepository.existsByEmail(request.email())){
			throw new RuntimeException("Email already register");
			
		}
		
		User user=User.builder()
					.fullName(request.fullName())
					.email(request.email())
					.password(request.password())
					.role(request.role())
					.phone(request.phone())
					.build();
		
		userRepository.save(user);
		
		String token = jwtUtil.generateToken(user);
		
		return new AuthResponse(token,
								user.getRole().name(),
								user.getFullName(),
								user.getEmail()
								);
	}
	
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.email(), request.password())
				);
		
		User user=userRepository.findByEmail(request.email())
				.orElseThrow(()->new RuntimeException("User not found"));
		
		String token= jwtUtil.generateToken(user);
		
		return new AuthResponse(
				token,
				user.getRole().name(),
				user.getFullName(),
				user.getEmail()
				);
	}
	
}
