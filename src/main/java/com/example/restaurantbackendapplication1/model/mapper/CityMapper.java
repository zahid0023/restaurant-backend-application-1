package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.city.CreateCityRequest;
import com.example.restaurantbackendapplication1.dto.request.city.UpdateCityRequest;
import com.example.restaurantbackendapplication1.model.dto.CityDto;
import com.example.restaurantbackendapplication1.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.model.entity.CityLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CityMapper {

    public static CityEntity fromRequest(CreateCityRequest request,
                                         CountryEntity countryEntity,
                                         Map<Long, LocaleEntity> localeEntityMap) {
        CityEntity entity = new CityEntity();
        entity.setCountryEntity(countryEntity);
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());

        if (request.getLocales() != null) {
            Set<CityLocaleEntity> cityLocaleEntities = request.getLocales().stream()
                    .map(localeRequest -> CityLocaleMapper.fromRequest(
                            localeRequest, entity, localeEntityMap.get(localeRequest.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setCityLocales(cityLocaleEntities);
        }
        return entity;
    }

    public static void update(CityEntity entity, UpdateCityRequest request) {
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
    }

    public static CityDto toDto(CityEntity entity) {
        CityDto dto = new CityDto();
        dto.setId(entity.getId());
        dto.setCountryId(entity.getCountryEntity().getId());
        dto.setCode(entity.getCode());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
