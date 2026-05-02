package com.example.restaurantbackendapplication1.auth.service;


import com.example.restaurantbackendapplication1.auth.dto.request.ResetPasswordRequest;
import com.example.restaurantbackendapplication1.auth.dto.response.VerifyOtpResponse;
import com.example.restaurantbackendapplication1.auth.model.enitty.UserEntity;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;

public interface PasswordResetService {
    SuccessResponse forgotPassword(UserEntity userEntity);

    SuccessResponse resetPassword(ResetPasswordRequest request);

    VerifyOtpResponse verifyOtpAndGetResetToken(UserEntity userEntity, String otp);
}

