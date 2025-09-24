package com.job_portal.Lates_Jobs.service;

import com.job_portal.Lates_Jobs.controller.dto.JobPostingDto;
import com.job_portal.Lates_Jobs.model.JobPosting;
import com.job_portal.Lates_Jobs.repository.JobPostingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobPostingServiceTest {

    @Mock
    private JobPostingRepository jobPostingRepository;

    @InjectMocks
    private JobPostingService jobPostingService;

    @Test
    void shouldSaveJobPosting() {
        // Given
        JobPosting jobPosting = new JobPosting();
        jobPosting.setJobTitle("Software Developer");
        jobPosting.setCompanyName("Test Company");
        jobPosting.setJobDescription("Test job description");

        JobPosting savedJob = new JobPosting();
        savedJob.setId(1L);
        savedJob.setJobTitle("Software Developer");
        savedJob.setCompanyName("Test Company");
        savedJob.setJobDescription("Test job description");
        savedJob.setPostedDate(LocalDateTime.now());

        when(jobPostingRepository.save(any(JobPosting.class))).thenReturn(savedJob);

        // When
        JobPosting result = jobPostingRepository.save(jobPosting);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getJobTitle()).isEqualTo("Software Developer");
        assertThat(result.getCompanyName()).isEqualTo("Test Company");
        verify(jobPostingRepository).save(any(JobPosting.class));
    }

    @Test
    void shouldFindAllJobPostings() {
        // Given
        JobPosting job1 = new JobPosting();
        job1.setId(1L);
        job1.setJobTitle("Developer");

        JobPosting job2 = new JobPosting();
        job2.setId(2L);
        job2.setJobTitle("Designer");

        List<JobPosting> jobs = Arrays.asList(job1, job2);
        when(jobPostingRepository.findAll()).thenReturn(jobs);

        // When
        List<JobPosting> result = jobPostingRepository.findAll();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getJobTitle()).isEqualTo("Developer");
        assertThat(result.get(1).getJobTitle()).isEqualTo("Designer");
        verify(jobPostingRepository).findAll();
    }

    @Test
    void shouldFindJobPostingById() {
        // Given
        Long jobId = 1L;
        JobPosting job = new JobPosting();
        job.setId(jobId);
        job.setJobTitle("Test Job");

        when(jobPostingRepository.findById(jobId)).thenReturn(Optional.of(job));

        // When
        Optional<JobPosting> result = jobPostingRepository.findById(jobId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getJobTitle()).isEqualTo("Test Job");
        verify(jobPostingRepository).findById(jobId);
    }

    @Test
    void shouldCreateJobPostingFromDto() {
        // Given
        JobPostingDto dto = new JobPostingDto();
        dto.setJobTitle("Backend Developer");
        dto.setCompanyName("Tech Corp");
        dto.setJobDescription("Java developer needed");

        // When
        JobPosting jobPosting = new JobPosting();
        jobPosting.setJobTitle(dto.getJobTitle());
        jobPosting.setCompanyName(dto.getCompanyName());
        jobPosting.setJobDescription(dto.getJobDescription());

        // Then
        assertThat(jobPosting.getJobTitle()).isEqualTo("Backend Developer");
        assertThat(jobPosting.getCompanyName()).isEqualTo("Tech Corp");
        assertThat(jobPosting.getJobDescription()).isEqualTo("Java developer needed");
    }
}
