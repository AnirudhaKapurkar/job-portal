package com.jobportal.job_portal.dto;

import java.time.LocalDateTime;

import com.jobportal.job_portal.enums.Application_status;

public record ApplicationResponse(
		Long id,
		String seekerName,
		String seekerEmail,
		String jobTitle,
		String company,
		Application_status status,
		String resumePath,
		LocalDateTime appliedAt
		) {

}
