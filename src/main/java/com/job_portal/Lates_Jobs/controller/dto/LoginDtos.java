package com.job_portal.Lates_Jobs.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class LoginDtos {
    public record LoginRequest(@NotEmpty @Email String email, @NotEmpty String password) {}
    public record LoginResponse(String jwt) {}
}
