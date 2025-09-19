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
        if (userOptional.isEmpty() || userOptional.get().getOtpExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        User user = userOptional.get();
        if (user.getOtp() != null && user.getOtp().equals(otp)) {
            user.setEnabled(true);
            user.setOtp(null);
            user.setOtpExpiryDate(null);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(1000000);
        return String.format("%06d", num);
    }
}
