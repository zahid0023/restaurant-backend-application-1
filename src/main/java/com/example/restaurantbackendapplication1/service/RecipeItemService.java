package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.recipeitem.CreateRecipeItemRequest;
import com.example.restaurantbackendapplication1.dto.request.recipeitem.UpdateRecipeItemRequest;
import com.example.restaurantbackendapplication1.dto.response.RecipeItemResponse;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.RecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.RecipeItemEntity;
import com.example.restaurantbackendapplication1.model.projection.RecipeItemSummary;

public interface RecipeItemService {
    SuccessResponse create(RecipeEntity recipeEntity, ItemEntity itemEntity, CreateRecipeItemRequest request);

    RecipeItemEntity getEntityById(Long id, RecipeEntity recipeEntity);

    RecipeItemResponse getById(Long id, RecipeEntity recipeEntity);

    PaginatedResponse<RecipeItemSummary> getAll(RecipeEntity recipeEntity, PaginatedRequest request);

    SuccessResponse update(RecipeItemEntity entity, ItemEntity itemEntity, UpdateRecipeItemRequest request);

    SuccessResponse delete(RecipeItemEntity entity);
}
