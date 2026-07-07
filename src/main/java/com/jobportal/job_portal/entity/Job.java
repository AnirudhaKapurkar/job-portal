package com.jobportal.job_portal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String title;
	
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
	@JoinColumn(name="recuiter_id",nullable = false)
	private User recruiter;
	
	private LocalDateTime postedAt;
	
	@PrePersist
	protected void onCreate() {
		postedAt=LocalDateTime.now();
	}
}
