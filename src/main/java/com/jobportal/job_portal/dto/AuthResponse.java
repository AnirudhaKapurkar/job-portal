package com.jobportal.job_portal.dto;

public record AuthResponse(
		String token,
		String role,
		String fullName,
		String email
		) {}
