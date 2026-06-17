package com.example.restaurantbackendapplication1.imagehosting.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.CreateRestaurantImageHostingConfigRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.UpdateRestaurantImageHostingConfigRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.response.RestaurantImageHostingConfigResponse;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import com.example.restaurantbackendapplication1.imagehosting.model.projection.RestaurantImageHostingConfigSummary;

public interface RestaurantImageHostingConfigService {

    SuccessResponse create(CreateRestaurantImageHostingConfigRequest request);

    RestaurantImageHostingConfigEntity getEntityById(Long id);

    RestaurantImageHostingConfigResponse getById(Long id);

    PaginatedResponse<RestaurantImageHostingConfigSummary> getAll(PaginatedRequest request);

    SuccessResponse update(RestaurantImageHostingConfigEntity entity, UpdateRestaurantImageHostingConfigRequest request);

    SuccessResponse delete(Long id);
}
