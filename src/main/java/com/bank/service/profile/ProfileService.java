package com.bank.service.profile;

import com.bank.dto.profile.ProfileResponse;
import com.bank.dto.profile.UpdateProfileRequest;

public interface ProfileService {

    ProfileResponse getProfile();

    ProfileResponse updateProfile(UpdateProfileRequest request);
}