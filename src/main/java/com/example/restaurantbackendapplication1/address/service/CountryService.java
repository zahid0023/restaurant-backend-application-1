package com.example.restaurantbackendapplication1.address.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.address.dto.request.country.CreateCountryRequest;
import com.example.restaurantbackendapplication1.address.dto.request.country.UpdateCountryRequest;
import com.example.restaurantbackendapplication1.address.dto.response.CountryResponse;
import com.example.restaurantbackendapplication1.address.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.address.model.projection.CountrySummary;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CountryService {
    SuccessResponse create(CreateCountryRequest request,
                           Map<Long, LocaleEntity> localeEntityMap);

    CountryEntity getEntityById(Long id);

    CountryResponse getById(Long id);

    PaginatedResponse<CountrySummary> getAll(PaginatedRequest request);

    SuccessResponse update(CountryEntity entity,
                           UpdateCountryRequest request);

    SuccessResponse delete(Long id);

    List<CountryEntity> getAll(Set<Long> ids);
}
