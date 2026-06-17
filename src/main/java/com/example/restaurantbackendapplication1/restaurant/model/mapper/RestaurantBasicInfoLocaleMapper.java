package com.example.restaurantbackendapplication1.restaurant.model.mapper;

import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale.CreateRestaurantBasicInfoLocaleRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale.RestaurantBasicInfoLocaleRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale.UpdateRestaurantBasicInfoLocaleRequest;
import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantBasicInfoLocaleDto;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoEntity;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoLocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestaurantBasicInfoLocaleMapper {

    public RestaurantBasicInfoLocaleEntity create(CreateRestaurantBasicInfoLocaleRequest request,
                                                  RestaurantBasicInfoEntity restaurantBasicInfoEntity,
                                                  LocaleEntity localeEntity) {
        RestaurantBasicInfoLocaleEntity entity = new RestaurantBasicInfoLocaleEntity();
        entity.setRestaurantBasicInfoEntity(restaurantBasicInfoEntity);
        entity.setLocaleEntity(localeEntity);
        applyCommonFields(entity, request);
        return entity;
    }

    public void update(RestaurantBasicInfoLocaleEntity entity, UpdateRestaurantBasicInfoLocaleRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(RestaurantBasicInfoLocaleEntity entity, RestaurantBasicInfoLocaleRequest request) {
        entity.setName(request.getName());
        entity.setShortDescription(request.getShortDescription());
        entity.setAddress(request.getAddress());
        entity.setSortOrder(request.getSortOrder());
    }

    public RestaurantBasicInfoLocaleDto toDto(RestaurantBasicInfoLocaleEntity entity) {
        return RestaurantBasicInfoLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .sortOrder(entity.getSortOrder())
                .name(entity.getName())
                .shortDescription(entity.getShortDescription())
                .address(entity.getAddress())
                .build();
    }
}
