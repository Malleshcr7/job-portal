package com.job_portal.Lates_Jobs.service;

import com.job_portal.Lates_Jobs.controller.dto.JobPostingDto;
import com.job_portal.Lates_Jobs.model.JobPosting;
import com.job_portal.Lates_Jobs.repository.JobPostingRepository;
import org.springframework.stereotype.Service;
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

    public JobPosting createJob(JobPostingDto dto) {
        JobPosting job = new JobPosting();
        job.setJobId(UUID.randomUUID().toString()); // Generate a unique ID
        updateJobFromDto(job, dto);
        return jobPostingRepository.save(job);
    }

    public Optional<JobPosting> updateJob(Long id, JobPostingDto dto) {
        return jobPostingRepository.findById(id)
                .map(job -> {
                    updateJobFromDto(job, dto);
                    return jobPostingRepository.save(job);
                });
    }

    public boolean deleteJob(Long id) {
        return jobPostingRepository.findById(id)
                .map(job -> {
                    jobPostingRepository.delete(job);
                    return true;
                }).orElse(false);
    }

    private void updateJobFromDto(JobPosting job, JobPostingDto dto) {
        job.setCompanyId(dto.companyId());
        job.setJobTitle(dto.jobTitle());
        job.setCompanyName(dto.companyName());
        job.setCompanyLogo(dto.companyLogo());
        job.setJobDescription(dto.jobDescription());
        job.setLocation(dto.location());
        job.setJobType(dto.jobType());
        job.setExperienceLevel(dto.experienceLevel());
        job.setCategory(dto.category());
        job.setIndustry(dto.industry());
        job.setSalary(dto.salary());
        job.setSkills(dto.skills());
        job.setBenefits(dto.benefits());
        job.setApplicationUrl(dto.applicationUrl());
        job.setApplicationDeadline(dto.applicationDeadline());
        job.setStatus(dto.status());
    }
}
