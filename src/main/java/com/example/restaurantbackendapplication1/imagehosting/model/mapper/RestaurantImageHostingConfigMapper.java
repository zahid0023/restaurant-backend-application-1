package com.example.restaurantbackendapplication1.imagehosting.model.mapper;

import com.example.restaurantbackendapplication1.imagehosting.dto.request.CreateRestaurantImageHostingConfigRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageHostingConfigRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.UpdateRestaurantImageHostingConfigRequest;
import com.example.restaurantbackendapplication1.imagehosting.model.dto.RestaurantImageHostingConfigDto;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestaurantImageHostingConfigMapper {

    public RestaurantImageHostingConfigEntity create(CreateRestaurantImageHostingConfigRequest request) {
        RestaurantImageHostingConfigEntity entity = new RestaurantImageHostingConfigEntity();
        applyCommonFields(entity, request);
        return entity;
    }

    public void update(RestaurantImageHostingConfigEntity entity,
                       UpdateRestaurantImageHostingConfigRequest request) {
        entity.setName(request.getName());
    }

    private void applyCommonFields(RestaurantImageHostingConfigEntity entity,
                                   ImageHostingConfigRequest request) {
        entity.setName(request.getName());
        entity.setProvider(request.getProvider());
        entity.setConfig(request.getConfig());
    }

    public RestaurantImageHostingConfigDto toDto(RestaurantImageHostingConfigEntity entity) {
        return RestaurantImageHostingConfigDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .provider(entity.getProvider())
                .config(entity.getConfig())
                .build();
    }
}
