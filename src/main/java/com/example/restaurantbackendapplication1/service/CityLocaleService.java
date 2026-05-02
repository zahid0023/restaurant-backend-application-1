package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.citylocale.CreateCityLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.citylocale.UpdateCityLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.CityLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.model.entity.CityLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.CityLocaleSummary;

public interface CityLocaleService {
    SuccessResponse create(CityEntity city, LocaleEntity locale, CreateCityLocaleRequest request);

    CityLocaleEntity getEntityById(Long id, CityEntity city);

    CityLocaleResponse getById(Long id, CityEntity city);

    PaginatedResponse<CityLocaleSummary> getAll(CityEntity city, PaginatedRequest request);

    SuccessResponse update(CityLocaleEntity entity, LocaleEntity locale, UpdateCityLocaleRequest request);

    SuccessResponse delete(CityLocaleEntity entity);
}
