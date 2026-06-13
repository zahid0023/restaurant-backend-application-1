package com.example.restaurantbackendapplication1.address.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.address.dto.request.citylocale.CreateCityLocaleRequest;
import com.example.restaurantbackendapplication1.address.dto.request.citylocale.UpdateCityLocaleRequest;
import com.example.restaurantbackendapplication1.address.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.address.model.entity.CityLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;

public interface CityLocaleService {
    SuccessResponse create(CityEntity city,
                           LocaleEntity localeEntity,
                           CreateCityLocaleRequest request);

    CityLocaleEntity getEntityById(
            Long countryId,
            Long cityId,
            Long id);

    SuccessResponse update(CityLocaleEntity entity,
                           UpdateCityLocaleRequest request);

    SuccessResponse delete(CityLocaleEntity entity);
}
