package com.example.restaurantbackendapplication1.dish.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantimage.UpdateDishVariantImageRequest;
import com.example.restaurantbackendapplication1.dish.dto.response.DishVariantImageResponse;
import com.example.restaurantbackendapplication1.dish.model.dto.DishVariantImageDto;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantImageEntity;
import com.example.restaurantbackendapplication1.dish.model.projection.DishVariantImageSummary;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageRequest;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;

import java.util.List;

public interface DishVariantImageService {

    List<DishVariantImageDto> createAll(List<ImageRequest> imageRequests,
                                        RestaurantImageHostingConfigEntity config,
                                        DishVariantEntity dishVariantEntity);

    DishVariantImageEntity getEntityById(Long dishVariantId, Long id);

    DishVariantImageResponse getById(Long dishVariantId, Long id);

    PaginatedResponse<DishVariantImageSummary> getAll(Long dishVariantId, PaginatedRequest request);

    SuccessResponse update(DishVariantImageEntity entity, UpdateDishVariantImageRequest request);

    SuccessResponse delete(DishVariantImageEntity entity);
}
