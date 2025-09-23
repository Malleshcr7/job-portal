package com.job_portal.Lates_Jobs.controller;

import com.job_portal.Lates_Jobs.model.JobPosting;
import com.job_portal.Lates_Jobs.service.JobPostingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final JobPostingService jobPostingService;

    public HomeController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Fetch all jobs and limit to the first 3 for the "Featured Jobs" section
        List<JobPosting> featuredJobs = jobPostingService.getAllJobs().stream().limit(3).collect(Collectors.toList());
        model.addAttribute("featuredJobs", featuredJobs);
        return "index";
    }
}
