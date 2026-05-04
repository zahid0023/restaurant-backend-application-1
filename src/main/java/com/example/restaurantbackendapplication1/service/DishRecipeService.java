package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishrecipe.CreateDishRecipeRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipe.UpdateDishRecipeRequest;
import com.example.restaurantbackendapplication1.dto.response.DishRecipeResponse;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.projection.DishRecipeSummary;

public interface DishRecipeService {

    SuccessResponse create(CreateDishRecipeRequest request, DishVariantEntity dishVariantEntity);

    DishRecipeEntity getEntityById(Long dishVariantId, Long id);

    DishRecipeResponse getById(Long dishVariantId, Long id);

    PaginatedResponse<DishRecipeSummary> getAll(Long dishVariantId, PaginatedRequest request);

    SuccessResponse update(DishRecipeEntity entity, UpdateDishRecipeRequest request);

    SuccessResponse delete(Long dishVariantId, Long id);
}
