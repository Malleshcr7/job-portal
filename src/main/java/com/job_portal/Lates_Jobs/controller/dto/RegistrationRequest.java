package com.job_portal.Lates_Jobs.controller.dto;

import com.job_portal.Lates_Jobs.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @NotEmpty @Email String email,
        @NotEmpty @Size(min = 8, message = "Password must be at least 8 characters long") String password,
        @NotNull Role role
) {}
