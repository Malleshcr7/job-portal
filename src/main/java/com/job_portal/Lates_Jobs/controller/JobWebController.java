package com.job_portal.Lates_Jobs.controller;

import com.job_portal.Lates_Jobs.model.JobPosting;
import com.job_portal.Lates_Jobs.service.JobPostingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/web/jobs")
public class JobWebController {

    private final JobPostingService jobPostingService;

    public JobWebController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    @GetMapping
    public String getAllJobs(Model model) {
        List<JobPosting> jobs = jobPostingService.getAllJobs();
        model.addAttribute("jobs", jobs);
        return "jobs";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/{id}")
    public String getJobById(@PathVariable Long id, Model model) {
        Optional<JobPosting> job = jobPostingService.getJobById(id);
        if (job.isPresent()) {
            model.addAttribute("job", job.get());
            return "job-details";
        } else {
            return "404"; // Or a specific error page
        }
    }
}