package com.bank.controller.profile;
import com.bank.dto.profile.UpdateProfileRequest;
import com.bank.dto.profile.ProfileResponse;
import com.bank.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {

        return ResponseEntity.ok(
                profileService.getProfile()
        );
    }
    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(
            @RequestBody UpdateProfileRequest request) {

        return ResponseEntity.ok(profileService.updateProfile(request));
    }
}