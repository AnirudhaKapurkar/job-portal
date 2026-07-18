package com.jobportal.job_portal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.job_portal.dto.ApplicationResponse;
import com.jobportal.job_portal.enums.ApplicationStatus;
import com.jobportal.job_portal.service.ApplicationService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
		
	private final ApplicationService applicationService;

	public ApplicationController(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@PostMapping("/apply/{jobId}")
	@PreAuthorize("hasAuthority('SEEKER')")
	public ResponseEntity<ApplicationResponse> apply(@PathVariable Long jobId){
		return ResponseEntity.ok(applicationService.applyToJob(jobId));
	}
	
	@GetMapping("/my-applications")
	@PreAuthorize("hasAuthority('SEEKER')")
	public ResponseEntity<List<ApplicationResponse>> getMyApplications(){
		return ResponseEntity.ok(applicationService.getMyApplications());
	}
	
	@GetMapping("/job/{jobId}")
	@PreAuthorize("hasAuthority('RECRUITER')")
	public ResponseEntity<List<ApplicationResponse>> getApplicationsForJob(
			@PathVariable Long jobId){
		return ResponseEntity.ok(applicationService.getApplicationsForJob(jobId));
	}
	
	@GetMapping("/job/{jobId}/status")
	@PreAuthorize("hasAuthority('RECRUITER')")
	public ResponseEntity<List<ApplicationResponse>> getApplicationsByStatus(
			@PathVariable Long jobId,
			@RequestParam ApplicationStatus status
			){
		return ResponseEntity.ok(applicationService.getApplicationsByStatus(jobId, status));
	}
	
	@PutMapping("/{applicationId}/status")
	@PreAuthorize("hasAuthority('RECRUITER')")
	public ResponseEntity<ApplicationResponse> updateStatus(
			@PathVariable Long applicationId,
			@RequestParam ApplicationStatus status
			){
		return ResponseEntity.ok(applicationService.updateStatus(applicationId, status));
	}
	
	
}
