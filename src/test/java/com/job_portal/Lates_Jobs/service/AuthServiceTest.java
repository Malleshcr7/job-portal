package com.job_portal.Lates_Jobs.service;

import com.job_portal.Lates_Jobs.model.User;
import com.job_portal.Lates_Jobs.model.Role;
import com.job_portal.Lates_Jobs.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class AuthServiceTest {

    @Test
    void contextLoads() {
        // Basic test to ensure context loads
        assertThat(true).isTrue();
    }
}
