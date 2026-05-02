package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.countrylocale.CreateCountryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.countrylocale.UpdateCountryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.CountryLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.model.entity.CountryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.CountryLocaleSummary;

public interface CountryLocaleService {
    SuccessResponse create(CountryEntity country, LocaleEntity locale, CreateCountryLocaleRequest request);
    CountryLocaleEntity getEntityById(Long id, CountryEntity country);
    CountryLocaleResponse getById(Long id, CountryEntity country);
    PaginatedResponse<CountryLocaleSummary> getAll(CountryEntity country, PaginatedRequest request);
    SuccessResponse update(CountryLocaleEntity entity, LocaleEntity locale, UpdateCountryLocaleRequest request);
    SuccessResponse delete(CountryLocaleEntity entity);
}
