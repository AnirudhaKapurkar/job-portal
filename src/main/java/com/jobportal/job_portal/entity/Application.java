package com.jobportal.job_portal.entity;

import java.time.LocalDateTime;

import com.jobportal.job_portal.enums.ApplicationStatus;

import jakarta.persistence.*;

@Entity
@Table(name="applications")
public class Application {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="seeker_id",nullable = false)
	private User seeker;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="job_id" , nullable = false)
	private Job job;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private ApplicationStatus status;
	
	private String resumePathAtApplication;
	 
	private LocalDateTime appliedAt;

	public Application() {
		
		// TODO Auto-generated constructor stub
	}

	public Application(long id, User seeker, Job job, ApplicationStatus status, String resumePathAtApplication,
			LocalDateTime appliedAt) {
		super();
		this.id = id;
		this.seeker = seeker;
		this.job = job;
		this.status = status;
		this.resumePathAtApplication = resumePathAtApplication;
		this.appliedAt = appliedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getSeeker() {
		return seeker;
	}

	public void setSeeker(User seeker) {
		this.seeker = seeker;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public String getResumePathAtApplication() {
		return resumePathAtApplication;
	}

	public void setResumePathAtApplication(String resumePathAtApplication) {
		this.resumePathAtApplication = resumePathAtApplication;
	}

	public LocalDateTime getAppliedAt() {
		return appliedAt;
	}

	public void setAppliedAt(LocalDateTime appliedAt) {
		this.appliedAt = appliedAt;
	}

	@PrePersist
	protected void onCreate() {
        appliedAt = LocalDateTime.now();
        status = ApplicationStatus.APPLIED;
    }
}
