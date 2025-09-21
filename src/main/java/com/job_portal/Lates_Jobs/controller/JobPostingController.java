package com.job_portal.Lates_Jobs.controller;

import com.job_portal.Lates_Jobs.controller.dto.JobPostingDto;
import com.job_portal.Lates_Jobs.model.JobPosting;
import com.job_portal.Lates_Jobs.service.JobPostingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    /**
     * Creates a new job posting.
     * This endpoint is protected and only accessible by users with the RECRUITER role.
     */
    @PostMapping
    public JobPosting createJob(@Valid @RequestBody JobPostingDto jobDto) {
        return jobPostingService.createJob(jobDto);
    }

    /**
     * Retrieves all job postings.
     * This endpoint is public and accessible by anyone.
     */
    @GetMapping
    public List<JobPosting> getAllJobs() {
        return jobPostingService.getAllJobs();
    }

    /**
     * Retrieves a single job posting by its ID.
     * This endpoint is public and accessible by anyone.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JobPosting> getJobById(@PathVariable(value = "id") Long jobId) {
        return jobPostingService.getJobById(jobId)
                .map(job -> ResponseEntity.ok().body(job))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing job posting.
     * This endpoint is protected and only accessible by users with the RECRUITER role.
     */
    @PutMapping("/{id}")
    public ResponseEntity<JobPosting> updateJob(@PathVariable(value = "id") Long jobId,
                                                @Valid @RequestBody JobPostingDto jobDetailsDto) {
        return jobPostingService.updateJob(jobId, jobDetailsDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a job posting.
     * This endpoint is protected and only accessible by users with the RECRUITER role.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable(value = "id") Long jobId) {
        if (jobPostingService.deleteJob(jobId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

