package com.example.restaurantbackendapplication1.address.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.address.dto.request.countrylocale.CreateCountryLocaleRequest;
import com.example.restaurantbackendapplication1.address.dto.request.countrylocale.UpdateCountryLocaleRequest;
import com.example.restaurantbackendapplication1.address.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.address.model.entity.CountryLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;

public interface CountryLocaleService {
    SuccessResponse create(CountryEntity countryEntity,
                           LocaleEntity localeEntity,
                           CreateCountryLocaleRequest request);

    CountryLocaleEntity getEntityById(Long countryId, Long id);

    SuccessResponse update(CountryLocaleEntity entity,
                           UpdateCountryLocaleRequest request);

    SuccessResponse delete(CountryLocaleEntity entity);
}
