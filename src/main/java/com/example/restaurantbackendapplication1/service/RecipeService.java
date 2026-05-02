package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.recipe.CreateRecipeRequest;
import com.example.restaurantbackendapplication1.dto.request.recipe.UpdateRecipeRequest;
import com.example.restaurantbackendapplication1.dto.response.RecipeResponse;
import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.RecipeEntity;
import com.example.restaurantbackendapplication1.model.projection.RecipeSummary;

public interface RecipeService {
    SuccessResponse create(MenuItemEntity menuItemEntity, CreateRecipeRequest request);

    RecipeEntity getEntityById(Long id, MenuItemEntity menuItemEntity);

    RecipeResponse getById(Long id, MenuItemEntity menuItemEntity);

    PaginatedResponse<RecipeSummary> getAll(MenuItemEntity menuItemEntity, PaginatedRequest request);

    SuccessResponse update(RecipeEntity entity, UpdateRecipeRequest request);

    SuccessResponse delete(RecipeEntity entity);
}
