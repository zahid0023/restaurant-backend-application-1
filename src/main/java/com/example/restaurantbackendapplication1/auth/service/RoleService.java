package com.example.restaurantbackendapplication1.auth.service;

import com.example.restaurantbackendapplication1.auth.dto.request.role.CreateRoleRequest;
import com.example.restaurantbackendapplication1.auth.model.enitty.RoleEntity;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;

public interface RoleService {
    SuccessResponse createRole(CreateRoleRequest request);

    RoleEntity getRoleEntity(String name);
}
