package com.jobportal.job_portal.dto;

import com.jobportal.job_portal.enums.Role;

import jakarta.validation.constraints.*;

public record RegisterRequest(
		@NotBlank(message="Full name is Required")
		String fullName,
		
		@NotBlank(message = "Email is required")
		@Email(message="Invalid email format")
		String email,
		
		@NotBlank(message = "Password is required")
		String password,
		
		@NotNull(message="role is required")
		Role role,
		
		String phone
		) {
	
}
