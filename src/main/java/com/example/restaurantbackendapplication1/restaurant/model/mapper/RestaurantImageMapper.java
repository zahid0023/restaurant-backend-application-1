package com.example.restaurantbackendapplication1.restaurant.model.mapper;

import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageRequest;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantimage.UpdateRestaurantImageRequest;
import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantImageDto;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantImageEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestaurantImageMapper {

    public RestaurantImageEntity create(ImageRequest request, RestaurantImageHostingConfigEntity config) {
        RestaurantImageEntity entity = new RestaurantImageEntity();
        entity.setConfigEntity(config);
        entity.setExternalId(request.getPublicId());
        entity.setUrl(request.getImageUrl());
        entity.setCaption(request.getCaption());
        entity.setSortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0);
        return entity;
    }

    public void update(RestaurantImageEntity entity, UpdateRestaurantImageRequest request) {
        entity.setCaption(request.getCaption());
        entity.setSortOrder(request.getSortOrder());
    }

    public RestaurantImageDto toDto(RestaurantImageEntity entity) {
        return RestaurantImageDto.builder()
                .id(entity.getId())
                .configId(entity.getConfigEntity().getId())
                .externalId(entity.getExternalId())
                .url(entity.getUrl())
                .caption(entity.getCaption())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
