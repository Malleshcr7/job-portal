package com.job_portal.Lates_Jobs.service;

import com.job_portal.Lates_Jobs.controller.dto.JobPostingDto;
import com.job_portal.Lates_Jobs.model.JobPosting;
import com.job_portal.Lates_Jobs.repository.JobPostingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;

    public JobPostingService(JobPostingRepository jobPostingRepository) {
        this.jobPostingRepository = jobPostingRepository;
    }

    public List<JobPosting> getAllJobs() {
        return jobPostingRepository.findAll();
    }

    public Optional<JobPosting> getJobById(Long id) {
        return jobPostingRepository.findById(id);
    }

    public JobPosting createJob(JobPostingDto jobDto) {
        JobPosting newJob = new JobPosting();
        // Manually map fields from DTO to the Entity
        newJob.setJobId(UUID.randomUUID().toString()); // Generate a unique ID for the job
        newJob.setCompanyId(jobDto.companyId());
        newJob.setJobTitle(jobDto.jobTitle());
        newJob.setCompanyName(jobDto.companyName());
        newJob.setCompanyLogo(jobDto.companyLogo());
        newJob.setJobDescription(jobDto.jobDescription());
        newJob.setLocation(jobDto.location());
        newJob.setJobType(jobDto.jobType());
        newJob.setExperienceLevel(jobDto.experienceLevel());
        newJob.setCategory(jobDto.category());
        newJob.setIndustry(jobDto.industry());
        newJob.setSalary(jobDto.salary());
        newJob.setSkills(jobDto.skills());
        newJob.setBenefits(jobDto.benefits());
        newJob.setApplicationUrl(jobDto.applicationUrl());
        newJob.setPostedDate(LocalDateTime.now()); // Set the post date to now
        newJob.setApplicationDeadline(jobDto.applicationDeadline());
        newJob.setStatus(jobDto.status());

        return jobPostingRepository.save(newJob);
    }

    public Optional<JobPosting> updateJob(Long id, JobPostingDto jobDetailsDto) {
        return jobPostingRepository.findById(id)
                .map(existingJob -> {
                    // Map fields from DTO to the existing entity for update
                    existingJob.setCompanyId(jobDetailsDto.companyId());
                    existingJob.setJobTitle(jobDetailsDto.jobTitle());
                    existingJob.setCompanyName(jobDetailsDto.companyName());
                    existingJob.setCompanyLogo(jobDetailsDto.companyLogo());
                    existingJob.setJobDescription(jobDetailsDto.jobDescription());
                    existingJob.setLocation(jobDetailsDto.location());
                    existingJob.setJobType(jobDetailsDto.jobType());
                    existingJob.setExperienceLevel(jobDetailsDto.experienceLevel());
                    existingJob.setCategory(jobDetailsDto.category());
                    existingJob.setIndustry(jobDetailsDto.industry());
                    existingJob.setSalary(jobDetailsDto.salary());
                    existingJob.setSkills(jobDetailsDto.skills());
                    existingJob.setBenefits(jobDetailsDto.benefits());
                    existingJob.setApplicationUrl(jobDetailsDto.applicationUrl());
                    existingJob.setApplicationDeadline(jobDetailsDto.applicationDeadline());
                    existingJob.setStatus(jobDetailsDto.status());
                    return jobPostingRepository.save(existingJob);
                });
    }

    public boolean deleteJob(Long id) {
        return jobPostingRepository.findById(id)
                .map(job -> {
                    jobPostingRepository.delete(job);
                    return true;
                }).orElse(false);
    }
}

