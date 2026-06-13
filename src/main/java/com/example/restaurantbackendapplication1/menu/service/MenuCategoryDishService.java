package com.example.restaurantbackendapplication1.menu.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dish.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryDishEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryEntity;

public interface MenuCategoryDishService {

    SuccessResponse assign(MenuCategoryEntity menuCategory, DishEntity dish);

    MenuCategoryDishEntity getEntityById(Long menuCategoryId, Long dishId);

    SuccessResponse unassign(MenuCategoryDishEntity entity);
}
