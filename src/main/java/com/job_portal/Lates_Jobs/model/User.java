package com.job_portal.Lates_Jobs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Size(min = 8)
    private String password;

    private boolean enabled = false;

    private String otp;
    private LocalDateTime otpExpiryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }
    public LocalDateTime getOtpExpiryDate() { return otpExpiryDate; }
    public void setOtpExpiryDate(LocalDateTime otpExpiryDate) { this.otpExpiryDate = otpExpiryDate; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
