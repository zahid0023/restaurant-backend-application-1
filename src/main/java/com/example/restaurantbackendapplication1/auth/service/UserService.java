package com.example.restaurantbackendapplication1.auth.service;

import com.example.restaurantbackendapplication1.auth.dto.request.RegistrationRequest;
import com.example.restaurantbackendapplication1.auth.model.enitty.UserEntity;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    SuccessResponse registerAdmin(RegistrationRequest request);

    SuccessResponse registerUser(RegistrationRequest request);

    UserEntity getUserById(Long id);

    SuccessResponse activateUser(UserEntity userEntity);

    Boolean existsSuperAdmin(String username);

    UserEntity getAuthenticatedUserEntity();

    UserEntity getUserByUsername(String username);
}
