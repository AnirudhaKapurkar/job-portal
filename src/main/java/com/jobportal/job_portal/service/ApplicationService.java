package com.jobportal.job_portal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jobportal.job_portal.dto.ApplicationResponse;
import com.jobportal.job_portal.entity.Application;
import com.jobportal.job_portal.entity.Job;
import com.jobportal.job_portal.entity.User;
import com.jobportal.job_portal.enums.ApplicationStatus;
import com.jobportal.job_portal.repository.ApplicationRepository;
import com.jobportal.job_portal.repository.JobRepository;
import com.jobportal.job_portal.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ApplicationService {
	
	private final ApplicationRepository applicationRepository;
	private final JobRepository jobRepository;
	private final UserRepository userrepository;
	private final EmailService emailService;
	public ApplicationService(ApplicationRepository applicationRepository, JobRepository jobRepository,
			UserRepository repository, EmailService emailService) {
		super();
		this.applicationRepository = applicationRepository;
		this.jobRepository = jobRepository;
		this.userrepository = repository;
		this.emailService = emailService;
	}
	
	
	public ApplicationResponse applyToJob(Long jobId) {
		User seeker= getCurrentUser();
		
		if(applicationRepository.existsBySeekerIdAndJobId(seeker.getId(), jobId)) {
			throw new RuntimeException("You have already applied to this job");
		}
		
		Job job= jobRepository.findById(jobId)
				.orElseThrow(()->new RuntimeException("Job not found"));
		
		if(!job.isActive()) {
			throw new RuntimeException("This Job is no longer accepting applications");
		}
		
		Application application=new Application();
		application.setSeeker(seeker);
		application.setJob(job);
		application.setResumePathAtApplication(seeker.getResumePath());
		
		Application saved  =applicationRepository.save(application);
		return mapToResponse(saved);
	}
	
	public List<ApplicationResponse> getMyApplications(){
		User seeker = getCurrentUser();
		return applicationRepository.findBySeekerId(seeker.getId())
									.stream()
									.map(this :: mapToResponse)
									.collect(Collectors.toList());
	}
	
	public List<ApplicationResponse> getApplicationsForJob(Long jobId){
		return applicationRepository.findByJobId(jobId)
				.stream()
				.map(this :: mapToResponse)
				.collect(Collectors.toList());
	}
	
	public List<ApplicationResponse> getApplicationsByStatus(Long jobId,ApplicationStatus status){
		return applicationRepository.findByJobIdAndStatus(jobId, status)
				.stream()
				.map(this :: mapToResponse)
				.collect(Collectors.toList());
	}
	
	
	@Transactional
	public ApplicationResponse updateStatus(Long applicationId, ApplicationStatus status) {
	    Application application = applicationRepository.findByIdWithDetails(applicationId)
	            .orElseThrow(() -> new RuntimeException("Application not found"));

	    application.setStatus(status);
	    applicationRepository.save(application);

	    if (status == ApplicationStatus.SHORTLISTED) {
	        emailService.sendShortListEmail(
	                application.getSeeker().getEmail(),
	                application.getSeeker().getFullName(),
	                application.getJob().getTitle(),
	                application.getJob().getCompany()
	        );
	    }

	    return mapToResponse(application);
	}
	
	private User getCurrentUser() {
		String email= SecurityContextHolder.getContext()
				.getAuthentication()
				.getName();
		return userrepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("User Not Found"));
	}
	
	private ApplicationResponse mapToResponse(Application application) {
		return new ApplicationResponse(
				application.getId(),
				application.getSeeker().getFullName(),
				application.getSeeker().getEmail(),
				application.getJob().getTitle(),
				application.getJob().getCompany(),
				application.getStatus(),
				application.getResumePathAtApplication(),
				application.getAppliedAt()
				);
	}
	
}
