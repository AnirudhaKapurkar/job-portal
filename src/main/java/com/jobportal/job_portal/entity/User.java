package com.jobportal.job_portal.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.jobportal.job_portal.enums.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String fullName;
	
	@Column(nullable=false,unique=true)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Role role;
	
	private String phone;
	
	private String resumePath;
	
	private LocalDateTime createdAt;
	
	@PrePersist
	protected void onCreate() {
		createdAt=LocalDateTime.now();
	}
}
