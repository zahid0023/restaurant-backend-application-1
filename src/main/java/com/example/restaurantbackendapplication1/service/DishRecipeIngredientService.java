package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.CreateDishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.UpdateDishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.dto.response.DishRecipeIngredientResponse;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeIngredientEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.projection.DishRecipeIngredientSummary;

public interface DishRecipeIngredientService {

    SuccessResponse create(CreateDishRecipeIngredientRequest request,
                           DishRecipeEntity dishRecipeEntity,
                           ItemEntity itemEntity);

    DishRecipeIngredientEntity getEntityById(Long dishRecipeId, Long id);

    DishRecipeIngredientResponse getById(Long dishRecipeId, Long id);

    PaginatedResponse<DishRecipeIngredientSummary> getAll(Long dishRecipeId, PaginatedRequest request);

    SuccessResponse update(DishRecipeIngredientEntity entity,
                           ItemEntity itemEntity,
                           UpdateDishRecipeIngredientRequest request);

    SuccessResponse delete(DishRecipeIngredientEntity entity);
}
