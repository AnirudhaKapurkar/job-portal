package com.jobportal.job_portal.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(
		@NotBlank(message = "Email is required")
		@Email(message = "Invalid email format")
		String email,
		
		@NotBlank(message = "Password is required")
		String password
		) {
	
}
