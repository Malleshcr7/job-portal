package com.job_portal.Lates_Jobs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String jobId; // e.g., a1b2c3d4

    @Column(nullable = false)
    private String companyId;

    @NotEmpty
    private String jobTitle;

    @NotEmpty
    private String companyName;

    @Column(nullable = true)
    private String companyLogo;

    @Lob // For large text fields
    @Column(nullable = false)
    private String jobDescription;

    @Embedded
    private Location location;

    private String jobType; // e.g., Full-time
    private String experienceLevel;
    private String category;
    private String industry;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "rangeMin", column = @Column(name = "salary_range_min")),
            @AttributeOverride(name = "rangeMax", column = @Column(name = "salary_range_max")),
            @AttributeOverride(name = "currency", column = @Column(name = "salary_currency")),
            @AttributeOverride(name = "period", column = @Column(name = "salary_period"))
    })
    private Salary salary;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "job_skills", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "skill")
    private List<String> skills;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "job_benefits", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "benefit")
    private List<String> benefits;

    private String applicationUrl;

    @CreationTimestamp
    private LocalDateTime postedDate;

    private LocalDateTime applicationDeadline;

    private String status; // e.g., published, draft, closed

    // Getters and Setters for all fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }
    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCompanyLogo() { return companyLogo; }
    public void setCompanyLogo(String companyLogo) { this.companyLogo = companyLogo; }
    public String getJobDescription() { return jobDescription; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }
    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
    public Salary getSalary() { return salary; }
    public void setSalary(Salary salary) { this.salary = salary; }
    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
    public List<String> getBenefits() { return benefits; }
    public void setBenefits(List<String> benefits) { this.benefits = benefits; }
    public String getApplicationUrl() { return applicationUrl; }
    public void setApplicationUrl(String applicationUrl) { this.applicationUrl = applicationUrl; }
    public LocalDateTime getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDateTime postedDate) { this.postedDate = postedDate; }
    public LocalDateTime getApplicationDeadline() { return applicationDeadline; }
    public void setApplicationDeadline(LocalDateTime applicationDeadline) { this.applicationDeadline = applicationDeadline; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
