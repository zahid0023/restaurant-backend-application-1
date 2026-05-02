package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.country.CreateCountryRequest;
import com.example.restaurantbackendapplication1.dto.request.country.UpdateCountryRequest;
import com.example.restaurantbackendapplication1.dto.response.CountryResponse;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.CountrySummary;
import com.example.restaurantbackendapplication1.model.entity.CountryEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CountryService {
    SuccessResponse create(CreateCountryRequest request, Map<Long, LocaleEntity> localeEntityMap);

    CountryEntity getEntityById(Long id);

    CountryResponse getById(Long id);

    PaginatedResponse<CountrySummary> getAll(PaginatedRequest request);

    SuccessResponse update(CountryEntity entity, UpdateCountryRequest request);

    SuccessResponse delete(Long id);

    List<CountryEntity> getAll(Set<Long> ids);
}
