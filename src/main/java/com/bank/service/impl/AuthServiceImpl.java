package com.bank.service.impl;

import com.bank.dto.LoginRequest;
import com.bank.dto.LoginResponse;
import com.bank.dto.RegisterRequest;
import com.bank.dto.RegisterResponse;
import com.bank.entity.Customer;
import com.bank.entity.Role;
import com.bank.entity.User;
import com.bank.repository.CustomerRepository;
import com.bank.repository.RoleRepository;
import com.bank.repository.UserRepository;
import com.bank.security.JwtService;
import com.bank.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        System.out.println("================================");
        System.out.println("Roles available in database:");

        roleRepository.findAll().forEach(role ->
                System.out.println(role.getId() + " -> " + role.getName())
        );

        System.out.println("================================");
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        System.out.println("Searching for ROLE_CUSTOMER...");

        Role role = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseThrow(() -> {
                    System.out.println("ROLE_CUSTOMER NOT FOUND!");
                    return new RuntimeException("ROLE_CUSTOMER not found.");
                });

        System.out.println("Role Found: " + role.getName());

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .enabled(true)
                .role(role)
                .build();

        User savedUser = userRepository.save(user);

        Customer customer = Customer.builder()
                .customerNumber("CUST" + (100000 + savedUser.getId()))
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .kycVerified(false)
                .user(savedUser)
                .build();

        customerRepository.save(customer);

        return new RegisterResponse(
                savedUser.getId(),
                "User Registered Successfully"
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        String email = request.getEmail().trim();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        System.out.println("================================");
        System.out.println("Login Email: " + email);
        System.out.println("Enabled: " + user.getEnabled());
        System.out.println("Role: " + user.getRole().getName());

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        System.out.println("Password Matches: " + matches);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        request.getPassword()
                )
        );

        System.out.println("Authentication Successful");

        String token = jwtService.generateToken(email);

        return new LoginResponse(token, "Login Successful");
    }
}