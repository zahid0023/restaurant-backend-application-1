package com.example.restaurantbackendapplication1.dish.model.mapper;

import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantimage.UpdateDishVariantImageRequest;
import com.example.restaurantbackendapplication1.dish.model.dto.DishVariantImageDto;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantImageEntity;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageRequest;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishVariantImageMapper {

    public DishVariantImageEntity create(ImageRequest request,
                                         RestaurantImageHostingConfigEntity config,
                                         DishVariantEntity dishVariantEntity) {
        DishVariantImageEntity entity = new DishVariantImageEntity();
        entity.setConfigEntity(config);
        entity.setDishVariantEntity(dishVariantEntity);
        entity.setExternalId(request.getPublicId());
        entity.setUrl(request.getImageUrl());
        entity.setCaption(request.getCaption());
        entity.setSortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0);
        return entity;
    }

    public void update(DishVariantImageEntity entity, UpdateDishVariantImageRequest request) {
        entity.setCaption(request.getCaption());
        entity.setSortOrder(request.getSortOrder());
    }

    public DishVariantImageDto toDto(DishVariantImageEntity entity) {
        return DishVariantImageDto.builder()
                .id(entity.getId())
                .url(entity.getUrl())
                .caption(entity.getCaption())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
