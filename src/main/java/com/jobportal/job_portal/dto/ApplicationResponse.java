package com.jobportal.job_portal.dto;

import java.time.LocalDateTime;

import com.jobportal.job_portal.enums.ApplicationStatus;

public record ApplicationResponse(
		Long id,
		String seekerName,
		String seekerEmail,
		String jobTitle,
		String company,
		ApplicationStatus status,
		String resumePath,
		LocalDateTime appliedAt
		) {

}
