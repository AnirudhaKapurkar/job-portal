package com.jobportal.job_portal.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jobportal.job_portal.entity.Job;
import com.jobportal.job_portal.repository.JobRepository;

@Component
public class JobExpiryScheduler {
	
	private final JobRepository jobRepository;

	public JobExpiryScheduler(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}
	
	@Scheduled(cron = "0 0 0 * * *")
	public void deactivateExpiredJobs() {
		List<Job> activeJobs = jobRepository.findByActiveTrue();
		
		for(Job job : activeJobs) {
			if(job.getClosingDate() != null &&
					!job.getClosingDate().isBefore(LocalDate.now())) {
				job.setActive(false);
				jobRepository.save(job);
			}
		}
	}
	
}
