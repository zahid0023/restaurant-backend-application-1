package com.example.restaurantbackendapplication1.restaurant.service;

import com.example.restaurantbackendapplication1.address.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.address.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.CreateRestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.UpdateRestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.response.RestaurantBasicInfoResponse;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoEntity;
import com.example.restaurantbackendapplication1.restaurant.model.projection.RestaurantBasicInfoSummary;

import java.util.Map;

public interface RestaurantBasicInfoService {
    SuccessResponse create(CreateRestaurantBasicInfoRequest request,
                           CountryEntity countryEntity,
                           CityEntity cityEntity,
                           Map<Long, LocaleEntity> localeEntityMap);

    RestaurantBasicInfoEntity getEntityById(Long id);

    RestaurantBasicInfoResponse getById(Long id);

    PaginatedResponse<RestaurantBasicInfoSummary> getAll(PaginatedRequest request);

    SuccessResponse update(RestaurantBasicInfoEntity entity, UpdateRestaurantBasicInfoRequest request);

    SuccessResponse delete(Long id);
}
