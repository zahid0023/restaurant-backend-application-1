package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.CreateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.UpdateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;

public interface ItemCategoryLocaleService {
    SuccessResponse create(ItemCategoryEntity itemCategoryEntity, LocaleEntity locale, CreateItemCategoryLocaleRequest request);

    ItemCategoryLocaleEntity getEntityById(Long id, ItemCategoryEntity itemCategoryEntity);

    SuccessResponse update(ItemCategoryLocaleEntity entity, UpdateItemCategoryLocaleRequest request);

    SuccessResponse delete(ItemCategoryLocaleEntity entity);
}
