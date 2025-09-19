package com.job_portal.Lates_Jobs.controller;

import com.job_portal.Lates_Jobs.controller.dto.LoginDtos;
import com.job_portal.Lates_Jobs.controller.dto.RegistrationRequest;
import com.job_portal.Lates_Jobs.controller.dto.VerifyRequest;
import com.job_portal.Lates_Jobs.service.AuthService;
import com.job_portal.Lates_Jobs.service.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        try {
            authService.registerUser(request.email(), request.password(), request.role());
            return ResponseEntity.ok(Map.of("message", "Registration successful. Please check your email for the OTP."));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyUser(@Valid @RequestBody VerifyRequest request) {
        boolean isVerified = authService.verifyOtp(request.email(), request.otp());
        if (isVerified) {
            return ResponseEntity.ok(Map.of("message", "Account verified successfully!"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or expired OTP."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDtos.LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.email());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginDtos.LoginResponse(jwt));
    }
}
