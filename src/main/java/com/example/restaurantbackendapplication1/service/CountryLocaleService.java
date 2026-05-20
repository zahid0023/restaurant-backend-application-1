package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.country.countrylocale.CreateCountryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.country.countrylocale.UpdateCountryLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.model.entity.CountryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;

public interface CountryLocaleService {
    SuccessResponse create(CountryEntity countryEntity,
                           LocaleEntity localeEntity,
                           CreateCountryLocaleRequest request);

    CountryLocaleEntity getEntityById(Long countryId, Long id);

    SuccessResponse update(CountryLocaleEntity entity,
                           UpdateCountryLocaleRequest request);

    SuccessResponse delete(CountryLocaleEntity entity);
}
