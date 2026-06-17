package com.example.restaurantbackendapplication1.restaurant.service;

import com.example.restaurantbackendapplication1.address.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.address.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.imagehosting.dto.response.ImageUploadResponse;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.UpdateRestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.response.RestaurantBasicInfoResponse;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoEntity;

public interface RestaurantBasicInfoService {

    RestaurantBasicInfoEntity getEntity();

    RestaurantBasicInfoResponse get();

    SuccessResponse update(RestaurantBasicInfoEntity entity,
                           UpdateRestaurantBasicInfoRequest request,
                           CountryEntity countryEntity,
                           CityEntity cityEntity);

    SuccessResponse uploadLogo(RestaurantBasicInfoEntity entity, ImageUploadResponse imageUploadResponse);
}
