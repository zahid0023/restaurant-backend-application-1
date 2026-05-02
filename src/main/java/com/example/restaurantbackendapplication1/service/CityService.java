package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.city.CreateCityRequest;
import com.example.restaurantbackendapplication1.dto.request.city.UpdateCityRequest;
import com.example.restaurantbackendapplication1.dto.response.CityResponse;
import com.example.restaurantbackendapplication1.model.projection.CitySummary;
import com.example.restaurantbackendapplication1.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;

import java.util.Map;

public interface CityService {
    SuccessResponse create(CreateCityRequest request, CountryEntity countryEntity, Map<Long, LocaleEntity> localeEntityMap);

    CityEntity getEntityById(Long id);

    CityResponse getById(Long id);

    PaginatedResponse<CitySummary> getAll(PaginatedRequest request);

    SuccessResponse update(CityEntity entity, UpdateCityRequest request);

    SuccessResponse delete(Long id);
}
