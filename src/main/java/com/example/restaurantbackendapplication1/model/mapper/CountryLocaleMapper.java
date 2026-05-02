package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.countrylocale.CountryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.countrylocale.UpdateCountryLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.CountryLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.model.entity.CountryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CountryLocaleMapper {

    public static CountryLocaleEntity fromRequest(
            CountryLocaleRequest request,
            CountryEntity countryEntity,
            LocaleEntity localeEntity) {
        CountryLocaleEntity entity = new CountryLocaleEntity();
        entity.setCountryEntity(countryEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(
            CountryLocaleEntity entity,
            UpdateCountryLocaleRequest request,
            LocaleEntity locale) {
        entity.setLocaleEntity(locale);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setSortOrder(request.getSortOrder());
    }

    public static CountryLocaleDto toDto(CountryLocaleEntity entity) {
        CountryLocaleDto dto = new CountryLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
