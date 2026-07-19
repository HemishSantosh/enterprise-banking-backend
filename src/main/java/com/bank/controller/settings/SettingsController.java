package com.bank.controller.settings;

import com.bank.dto.settings.ChangePasswordRequest;
import com.bank.service.settings.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request) {

        settingsService.changePassword(request);

        return ResponseEntity.ok(
                "Password changed successfully");
    }
}