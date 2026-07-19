package com.bank.service.settings;

import com.bank.dto.settings.ChangePasswordRequest;

public interface SettingsService {

    void changePassword(ChangePasswordRequest request);

}