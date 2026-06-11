package com.booking.service;

import com.booking.dto.request.LoginRequest;
import com.booking.dto.request.RegisterRequest;
import com.booking.dto.response.LoginResponse;
import com.booking.dto.response.UserProfileResponse;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserProfileResponse getProfile();

    void logout();
}