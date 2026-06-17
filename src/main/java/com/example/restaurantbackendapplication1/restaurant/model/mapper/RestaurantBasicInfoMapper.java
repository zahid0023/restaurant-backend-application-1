package com.example.restaurantbackendapplication1.restaurant.model.mapper;

import com.example.restaurantbackendapplication1.address.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.address.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.CreateRestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.RestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.UpdateRestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale.CreateRestaurantBasicInfoLocaleRequest;
import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantBasicInfoDto;
import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantBasicInfoLocaleDto;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoEntity;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoLocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantBasicInfoMapper {

    public RestaurantBasicInfoEntity create(CreateRestaurantBasicInfoRequest request,
                                            CountryEntity countryEntity,
                                            CityEntity cityEntity,
                                            Map<Long, LocaleEntity> localeEntityMap) {
        RestaurantBasicInfoEntity entity = new RestaurantBasicInfoEntity();
        entity.setCode(request.getCode());
        entity.setCountryEntity(countryEntity);
        entity.setCityEntity(cityEntity);
        applyCommonFields(entity, request);

        if (request.getLocales() != null) {
            Set<RestaurantBasicInfoLocaleEntity> locales = request.getLocales().stream()
                    .map(l -> RestaurantBasicInfoLocaleMapper.create(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setRestaurantBasicInfoLocaleEntities(locales);
        }
        return entity;
    }

    public void update(RestaurantBasicInfoEntity entity, UpdateRestaurantBasicInfoRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(RestaurantBasicInfoEntity entity, RestaurantBasicInfoRequest request) {
        entity.setSortOrder(request.getSortOrder());
        entity.setPhone(request.getPhone());
        entity.setEmail(request.getEmail());
        entity.setLogoUrl(request.getLogoUrl());
    }

    public RestaurantBasicInfoDto toDto(RestaurantBasicInfoEntity entity) {
        List<RestaurantBasicInfoLocaleDto> locales = entity.getRestaurantBasicInfoLocaleEntities().stream()
                .map(RestaurantBasicInfoLocaleMapper::toDto)
                .toList();

        return RestaurantBasicInfoDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .countryId(entity.getCountryEntity().getId())
                .cityId(entity.getCityEntity().getId())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .logoUrl(entity.getLogoUrl())
                .locales(locales)
                .build();
    }
}
