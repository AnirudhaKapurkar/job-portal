package com.jobportal.job_portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobportal.job_portal.entity.Application;
import com.jobportal.job_portal.enums.Application_status;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>{
	
	List<Application> findByseekerId(Long seekerId);
	
	List<Application> findByJobId(Long jobId);
	
	List<Application> findByJobIdAndStatus(Long jobId,Application_status status);
	
	boolean existsBySeekerIdAndJobId(Long seekerId,Long jobId);
}
