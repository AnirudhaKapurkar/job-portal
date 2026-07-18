package com.jobportal.job_portal.repository;

import com.jobportal.job_portal.entity.Application;
import com.jobportal.job_portal.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT a FROM Application a JOIN FETCH a.seeker JOIN FETCH a.job WHERE a.seeker.id = :seekerId")
    List<Application> findBySeekerId(@Param("seekerId") Long seekerId);

    @Query("SELECT a FROM Application a JOIN FETCH a.seeker JOIN FETCH a.job WHERE a.job.id = :jobId")
    List<Application> findByJobId(@Param("jobId") Long jobId);

    @Query("SELECT a FROM Application a JOIN FETCH a.seeker JOIN FETCH a.job WHERE a.job.id = :jobId AND a.status = :status")
    List<Application> findByJobIdAndStatus(@Param("jobId") Long jobId, @Param("status") ApplicationStatus status);

    @Query("SELECT a FROM Application a JOIN FETCH a.seeker JOIN FETCH a.job WHERE a.id = :id")
    Optional<Application> findByIdWithDetails(@Param("id") Long id);
    
    boolean existsBySeekerIdAndJobId(Long seekerId, Long jobId);
}