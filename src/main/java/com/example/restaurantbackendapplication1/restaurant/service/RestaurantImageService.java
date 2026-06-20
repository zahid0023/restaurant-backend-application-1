package com.example.restaurantbackendapplication1.restaurant.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageRequest;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantimage.UpdateRestaurantImageRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.response.RestaurantImageResponse;
import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantImageDto;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantImageEntity;
import com.example.restaurantbackendapplication1.restaurant.model.projection.RestaurantImageSummary;

import java.util.List;

public interface RestaurantImageService {

    List<RestaurantImageDto> createAll(List<ImageRequest> imageRequests, RestaurantImageHostingConfigEntity config);

    RestaurantImageEntity getEntityById(Long id);

    RestaurantImageResponse getById(Long id);

    PaginatedResponse<RestaurantImageSummary> getAll(PaginatedRequest request);

    SuccessResponse update(RestaurantImageEntity entity, UpdateRestaurantImageRequest request);

    SuccessResponse delete(RestaurantImageEntity entity);
}
