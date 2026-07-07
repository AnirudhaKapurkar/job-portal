package com.jobportal.job_portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobportal.job_portal.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
	List<Job> findByActiveTrue();
	
	List<Job> findByRecruiterId(Long recruiterId);
	
	List<Job> findByTitleContainingIgnoreCaseAndActiveTrue(String title);
	
	List<Job> findByLocationContainingIgnoreCaseActiveTrue(String location);
}
