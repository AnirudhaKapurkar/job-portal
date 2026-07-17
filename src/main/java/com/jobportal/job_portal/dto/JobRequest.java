package com.jobportal.job_portal.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;

public record JobRequest(
		@NotBlank(message="Job Title is required")
		String title,
		@NotBlank(message = "Company name is required")
		String company,
		
		@NotBlank(message = "Description is required")
		String description,
		
		@NotBlank(message = "Location is required")
		String location,
		
		String salary,
		
		String experienceRequired,
		
		@NotNull(message = "Closing date is required")
		LocalDate closingDate
		) {

}
