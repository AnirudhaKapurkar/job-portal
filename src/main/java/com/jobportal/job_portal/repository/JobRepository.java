package com.jobportal.job_portal.repository;

import com.jobportal.job_portal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT j FROM Job j JOIN FETCH j.recruiter WHERE j.active = true")
    List<Job> findByActiveTrue();

    @Query("SELECT j FROM Job j JOIN FETCH j.recruiter WHERE j.recruiter.id = :recruiterId")
    List<Job> findByRecruiterId(@Param("recruiterId") Long recruiterId);

    @Query("SELECT j FROM Job j JOIN FETCH j.recruiter WHERE LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%')) AND j.active = true")
    List<Job> findByTitleContainingIgnoreCaseAndActiveTrue(@Param("title") String title);

    @Query("SELECT j FROM Job j JOIN FETCH j.recruiter WHERE LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')) AND j.active = true")
    List<Job> findByLocationContainingIgnoreCaseAndActiveTrue(@Param("location") String location);
}