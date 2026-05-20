package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.CreateDishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.UpdateDishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeIngredientEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;

public interface DishRecipeIngredientService {

    SuccessResponse create(CreateDishRecipeIngredientRequest request,
                           DishRecipeEntity dishRecipeEntity,
                           ItemEntity itemEntity,
                           UnitEntity unitEntity);

    DishRecipeIngredientEntity getEntityById(Long dishRecipeId, Long id);

    SuccessResponse update(DishRecipeIngredientEntity entity,
                           ItemEntity itemEntity,
                           UnitEntity unitEntity,
                           UpdateDishRecipeIngredientRequest request);

    SuccessResponse delete(DishRecipeIngredientEntity entity);
}
