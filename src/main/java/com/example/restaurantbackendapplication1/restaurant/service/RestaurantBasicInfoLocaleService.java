package com.example.restaurantbackendapplication1.restaurant.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale.CreateRestaurantBasicInfoLocaleRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale.UpdateRestaurantBasicInfoLocaleRequest;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoEntity;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoLocaleEntity;

public interface RestaurantBasicInfoLocaleService {
    SuccessResponse create(RestaurantBasicInfoEntity restaurantBasicInfoEntity,
                           LocaleEntity localeEntity,
                           CreateRestaurantBasicInfoLocaleRequest request);

    RestaurantBasicInfoLocaleEntity getEntityById(Long restaurantBasicInfoId, Long id);

    SuccessResponse update(RestaurantBasicInfoLocaleEntity entity, UpdateRestaurantBasicInfoLocaleRequest request);

    SuccessResponse delete(RestaurantBasicInfoLocaleEntity entity);
}
