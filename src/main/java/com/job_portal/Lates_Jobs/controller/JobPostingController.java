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

    // Anyone can get jobs
    @GetMapping
    public List<JobPosting> getAllJobs() {
        return jobPostingService.getAllJobs();
    }

    // Anyone can get a single job
    @GetMapping("/{id}")
    public ResponseEntity<JobPosting> getJobById(@PathVariable(value = "id") Long jobId) {
        return jobPostingService.getJobById(jobId)
                .map(job -> ResponseEntity.ok().body(job))
                .orElse(ResponseEntity.notFound().build());
    }

    // Only recruiters can create jobs
    @PostMapping
    public JobPosting createJob(@Valid @RequestBody JobPostingDto jobDto) {
        return jobPostingService.createJob(jobDto);
    }

    // Only recruiters can update jobs
    @PutMapping("/{id}")
    public ResponseEntity<JobPosting> updateJob(@PathVariable(value = "id") Long jobId,
                                                @Valid @RequestBody JobPostingDto jobDetails) {
        return jobPostingService.updateJob(jobId, jobDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Only recruiters can delete jobs
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable(value = "id") Long jobId) {
        if (jobPostingService.deleteJob(jobId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
