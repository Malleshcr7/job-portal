package com.job_portal.Lates_Jobs.service;

import com.job_portal.Lates_Jobs.controller.dto.RegistrationRequest;
import com.job_portal.Lates_Jobs.model.Role;
import com.job_portal.Lates_Jobs.model.User;
import com.job_portal.Lates_Jobs.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldCreateUser() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(Role.USER);

        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User createdUser = userRepository.save(user);

        // Then
        assertThat(createdUser.getEmail()).isEqualTo("test@example.com");
        verify(userRepository).save(user);
    }

    @Test
    void shouldFindUserByEmail() {
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // When
        Optional<User> found = userRepository.findByEmail(email);

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo(email);
        verify(userRepository).findByEmail(email);
    }

    @Test
    void shouldEncodePassword() {
        // Given
        String password = "plainPassword";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        // When
        String result = passwordEncoder.encode(password);

        // Then
        assertThat(result).isEqualTo(encodedPassword);
        verify(passwordEncoder).encode(password);
    }
}
