package com.job_portal.Lates_Jobs.repository;

import com.job_portal.Lates_Jobs.model.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    // Spring Data JPA will provide all standard CRUD methods automatically
}