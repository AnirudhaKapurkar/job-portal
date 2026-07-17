package com.jobportal.job_portal.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jobportal.job_portal.dto.JobRequest;
import com.jobportal.job_portal.dto.JobResponse;
import com.jobportal.job_portal.entity.Job;
import com.jobportal.job_portal.entity.User;
import com.jobportal.job_portal.repository.JobRepository;
import com.jobportal.job_portal.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class JobService {
	private final JobRepository jobRepository;
	private final UserRepository userRepository;
	
	public JobService(JobRepository jobRepository, UserRepository userRepository) {
		this.jobRepository = jobRepository;
		this.userRepository = userRepository;
	}
	
	public JobResponse postJob(JobRequest request ) {
		User recruiter = getCurrentUser();
		
		Job job = new Job();
		job.setTitle(request.title());
		job.setCompany(request.company());
		job.setDescription(request.description());
		job.setLocation(request.location());
		job.setSalary(request.salary());
		job.setExperienceRequired(request.experienceRequired());
		job.setRecruiter(recruiter);
		
		Job saved = jobRepository.save(job);
		return mapToResponse(saved);
		
	}
	
	public List<JobResponse> getAllActiveJobs(){
		return jobRepository.findByActiveTrue()
				.stream()
				.map(this :: mapToResponse)
				.collect(Collectors.toList());
	}
	
	public List<JobResponse> getJobsByRecruiter(){
		User recruiter = getCurrentUser();
		
		List<Job> allJobs = jobRepository.findByRecruiterId(recruiter.getId());
		
		List<JobResponse> activeJobs = allJobs.stream()
									.filter(Job :: isActive)
									.sorted(Comparator.comparing(Job::getPostedAt).reversed())
									.map(this :: mapToResponse)
									.collect(Collectors.toList());
		
		List<JobResponse> expiredJobs = allJobs.stream()
								.filter(job -> !job.isActive())
								.sorted(Comparator.comparing(Job :: getPostedAt).reversed())
								.map(this :: mapToResponse)
								.collect(Collectors.toList());
		
		activeJobs.addAll(expiredJobs);
		return activeJobs;
	}
	
	public List<JobResponse> searchByTitle(String title){
		return jobRepository.findByTitleContainingIgnoreCaseAndActiveTrue(title)
				.stream()
				.map(this :: mapToResponse)
				.collect(Collectors.toList());
	}
	
	public List<JobResponse> searchByLocation(String location){
		return jobRepository.findByLocationContainingIgnoreCaseAndActiveTrue(location)
							.stream()
							.map(this :: mapToResponse)
							.collect(Collectors.toList());
	}
	
	public JobResponse getJobById(Long id) {
		Job job= jobRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Job not found"));
		return mapToResponse(job);
	}
	
	public void deactivateJob(Long id) {
		Job job = jobRepository.findById(id)
				.orElseThrow(()->new RuntimeException("Job not found"));
	
		User recruiter = getCurrentUser();
		if(!job.getRecruiter().getId().equals(recruiter.getId())) {
			throw new RuntimeException("Not authorized");
		}
		
		job.setActive(false);
		jobRepository.save(job);
	
	}
	
	private User getCurrentUser() {
		String email = SecurityContextHolder.getContext()
				.getAuthentication()
				.getName();
		return userRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("User not found"));
	}
	
	private JobResponse mapToResponse(Job job) {
		return new JobResponse(
				job.getId(),
				job.getTitle(),
				job.getCompany(),
				job.getDescription(),
				job.getLocation(),
				job.getSalary(),
				job.getExperienceRequired(),
				job.isActive(),
				job.getRecruiter().getFullName(),
				job.getPostedAt(),
				job.getClosingDate()
				);
	}
	
}
