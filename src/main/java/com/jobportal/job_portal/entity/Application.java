package com.jobportal.job_portal.entity;

import java.time.LocalDateTime;

import com.jobportal.job_portal.enums.Application_status;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="seeker_id",nullable = false)
	private User seeker;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="job_id" , nullable = false)
	private Job job;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Application_status status;
	
	private String resumePathAtApplication;
	 
	private LocalDateTime appliedAt;

	@PrePersist
	protected void onCreate() {
        appliedAt = LocalDateTime.now();
        status = Application_status.APPLIED;
    }
}
