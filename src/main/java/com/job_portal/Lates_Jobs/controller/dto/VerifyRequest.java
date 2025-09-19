package com.job_portal.Lates_Jobs.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

// This class defines the structure for the OTP verification request
public record VerifyRequest(
        @NotEmpty @Email String email,
        @NotEmpty String otp
) {}