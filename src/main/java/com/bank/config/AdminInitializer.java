package com.bank.config;

import com.bank.entity.Role;
import com.bank.entity.User;
import com.bank.repository.RoleRepository;
import com.bank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (!userRepository.existsByEmail("admin@bank.com")) {

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

            User admin = User.builder()
                    .firstName("System")
                    .lastName("Administrator")
                    .email("admin@bank.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .phone("9999999999")
                    .enabled(true)
                    .role(adminRole)
                    .build();

            userRepository.save(admin);

            System.out.println("=====================================");
            System.out.println("Admin user created successfully!");
            System.out.println("Email: admin@bank.com");
            System.out.println("Password: Admin@123");
            System.out.println("=====================================");
        }
    }
}