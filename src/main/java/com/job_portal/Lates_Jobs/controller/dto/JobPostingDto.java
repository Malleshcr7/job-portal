package com.job_portal.Lates_Jobs.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class JobPostingDto {

    @NotEmpty(message = "Job title cannot be empty")
    private String jobTitle;

    @NotEmpty(message = "Company name cannot be empty")
    private String companyName;

    @NotEmpty(message = "Job description cannot be empty")
    private String jobDescription;

    private String jobType;
    private String category;
    private String experienceLevel;
    private String industry;

    // Constructors
    public JobPostingDto() {}

    // Getters
    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getJobType() {
        return jobType;
    }

    public String getCategory() {
        return category;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public String getIndustry() {
        return industry;
    }

    // Setters - ADD THESE MISSING METHODS
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
