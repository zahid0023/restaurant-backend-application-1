package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.country.CreateCountryRequest;
import com.example.restaurantbackendapplication1.dto.request.country.UpdateCountryRequest;
import com.example.restaurantbackendapplication1.model.dto.CountryDto;
import com.example.restaurantbackendapplication1.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.model.entity.CountryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CountryMapper {

    public static CountryEntity fromRequest(CreateCountryRequest request,
                                            Map<Long, LocaleEntity> localeEntityMap) {
        CountryEntity entity = new CountryEntity();
        entity.setCode(request.getCode());
        entity.setIso3Code(request.getIso3Code());
        entity.setPhoneCode(request.getPhoneCode());
        entity.setSortOrder(request.getSortOrder());

        Set<CountryLocaleEntity> countryLocaleEntities = request.getLocales().stream()
                .map(countryLocaleRequest -> CountryLocaleMapper.fromRequest(countryLocaleRequest, entity, localeEntityMap.get(countryLocaleRequest.getLocaleId())))
                .collect(Collectors.toSet());
        entity.setCountryLocales(countryLocaleEntities);
        return entity;
    }

    public static void update(CountryEntity entity, UpdateCountryRequest request) {
        entity.setCode(request.getCode());
        entity.setIso3Code(request.getIso3Code());
        entity.setPhoneCode(request.getPhoneCode());
        entity.setSortOrder(request.getSortOrder());
    }

    public static CountryDto toDto(CountryEntity entity) {
        CountryDto dto = new CountryDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setIso3Code(entity.getIso3Code());
        dto.setPhoneCode(entity.getPhoneCode());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
