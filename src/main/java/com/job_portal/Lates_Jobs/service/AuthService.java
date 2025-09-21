package com.job_portal.Lates_Jobs.service;

import com.job_portal.Lates_Jobs.model.Role;
import com.job_portal.Lates_Jobs.model.User;
import com.job_portal.Lates_Jobs.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public User registerUser(String email, String rawPassword, Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Email already in use");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role); // Set the user's role
        user.setEnabled(false);

        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiryDate(LocalDateTime.now().plusMinutes(10));

        User savedUser = userRepository.save(user);

        String emailBody = "Your verification OTP is: " + otp;
        emailService.sendEmail(user.getEmail(), "Verify your Job Portal Account", emailBody);

        return savedUser;
    }

    public boolean verifyOtp(String email, String otp) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            System.out.println("Verification failed: User not found with email: " + email);
            return false;
        }

        User user = userOptional.get();

        if (user.getOtpExpiryDate().isBefore(LocalDateTime.now())) {
            System.out.println("Verification failed: OTP has expired for user: " + email);
            return false;
        }

        // --- ADD THIS LOGGING ---
        System.out.println("--- OTP Verification Debug ---");
        System.out.println("Submitted OTP: [" + otp + "]");
        System.out.println("Database OTP:  [" + user.getOtp() + "]");
        System.out.println("----------------------------");

        if (user.getOtp() != null && user.getOtp().equals(otp)) {
            System.out.println("Verification successful for user: " + email);
            user.setEnabled(true);
            user.setOtp(null);
            user.setOtpExpiryDate(null);
            userRepository.save(user);
            return true;
        } else {
            System.out.println("Verification failed: OTPs do not match for user: " + email);
            return false;
        }
    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(1000000);
        return String.format("%06d", num);
    }
}

