package com.jobportal.job_portal.dto;

import java.time.LocalDateTime;

public record JobResponse(
		Long id,
		String title,
		String company,
		String description,
		String location,
		String salary,
		String experienceRequired,
		boolean active,
		String recruiterName,
		LocalDateTime postedAt
		) {
	
}
