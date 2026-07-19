package com.bank.service;

import com.bank.dto.LoginRequest;
import com.bank.dto.LoginResponse;
import com.bank.dto.RegisterRequest;
import com.bank.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}