package com.job_portal.Lates_Jobs.controller.dto;

import com.job_portal.Lates_Jobs.model.Location;
import com.job_portal.Lates_Jobs.model.Salary;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public record JobPostingDto(
        @NotEmpty String companyId,
        @NotEmpty String jobTitle,
        @NotEmpty String companyName,
        String companyLogo, // optional
        @NotEmpty String jobDescription,
        Location location,
        String jobType,
        String experienceLevel,
        String category,
        String industry,
        Salary salary, // optional
        List<String> skills,
        List<String> benefits, // optional
        String applicationUrl,
        LocalDateTime applicationDeadline, // optional
        @NotEmpty String status
) {}
