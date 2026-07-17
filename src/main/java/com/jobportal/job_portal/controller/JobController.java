package com.jobportal.job_portal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.job_portal.dto.JobRequest;
import com.jobportal.job_portal.dto.JobResponse;
import com.jobportal.job_portal.service.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
	
	private final JobService jobService;

	public JobController(JobService jobService) {
		this.jobService = jobService;
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('RECRUITER')")
	public ResponseEntity<JobResponse> postJob(
			@Valid @RequestBody JobRequest request
			)
	{
		return ResponseEntity.ok(jobService.postJob(request));
	}
	
	@GetMapping
	public ResponseEntity<List<JobResponse>> getAllActiveJobs(){
		return ResponseEntity.ok(jobService.getAllActiveJobs());
	}
	
	
	
	@GetMapping("/my-jobs")
	@PreAuthorize("hasAuthority('RECRUITER')")
	public ResponseEntity<List<JobResponse>> getMyJobs(){
		return ResponseEntity.ok(jobService.getJobsByRecruiter());
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<JobResponse>> search(
			@RequestParam(required= false) String title,
			@RequestParam(required = false) String location
			){
		if(title !=null && !title.isEmpty()) {
			return ResponseEntity.ok(jobService.searchByTitle(title));
		}else if(location != null && !location.isEmpty()) {
			return ResponseEntity.ok(jobService.searchByLocation(location));
		}else {
			return ResponseEntity.ok(jobService.getAllActiveJobs());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<JobResponse> getJobById(
			@PathVariable Long id
			){
		return ResponseEntity.ok(jobService.getJobById(id));
	}
	
	
	
	
	@PutMapping("/{id}/deactivate")
	@PreAuthorize("hasAuthority('RECRUITER')")
	public ResponseEntity<String> deactivateJob(@PathVariable Long id){
		jobService.deactivateJob(id);
		return ResponseEntity.ok("Job deactivated Successfully");
	}
	
}
