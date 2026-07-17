package com.jobportal.job_portal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name="jobs")
public class Job {
	
	public Job() {
		
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String title;
	
	private java.time.LocalDate closingDate;
	
	public java.time.LocalDate getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(java.time.LocalDate closingDate) {
		this.closingDate = closingDate;
	}

	public long getId() {
		return id;
	}

	public Job(long id, String title, String company, String description, String location, String salary,
			String experienceRequired, boolean active, User recruiter, LocalDateTime postedAt) {
		super();
		this.id = id;
		this.title = title;
		this.company = company;
		this.description = description;
		this.location = location;
		this.salary = salary;
		this.experienceRequired = experienceRequired;
		this.active = active;
		this.recruiter = recruiter;
		this.postedAt = postedAt;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getExperienceRequired() {
		return experienceRequired;
	}

	public void setExperienceRequired(String experienceRequired) {
		this.experienceRequired = experienceRequired;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public User getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(User recruiter) {
		this.recruiter = recruiter;
	}

	public LocalDateTime getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(LocalDateTime postedAt) {
		this.postedAt = postedAt;
	}

	@Column(nullable=false)
	private String company;
	
	@Column(nullable=false,length=2000)
	private String description;
	
	@Column(nullable = false)
	private String location;
	
	private String salary;
	
	private String experienceRequired;
	
	@Column(nullable=false)
	private boolean active=true;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="recruiter_id",nullable = false)
	private User recruiter;
	
	private LocalDateTime postedAt;
	
	@PrePersist
	protected void onCreate() {
		postedAt=LocalDateTime.now();
	}
}
