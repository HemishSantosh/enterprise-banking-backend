package com.bank.service.impl.settings;

import com.bank.dto.settings.ChangePasswordRequest;
import com.bank.entity.User;
import com.bank.repository.UserRepository;
import com.bank.service.settings.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void changePassword(ChangePasswordRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {

            throw new RuntimeException("Current password is incorrect");
        }

        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {

            throw new RuntimeException("Passwords do not match");
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }
}