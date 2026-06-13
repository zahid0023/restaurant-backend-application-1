package com.example.restaurantbackendapplication1.item.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.item.dto.request.itemcategorylocale.CreateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemcategorylocale.UpdateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.item.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;

public interface ItemCategoryLocaleService {
    SuccessResponse create(ItemCategoryEntity itemCategoryEntity, LocaleEntity locale, CreateItemCategoryLocaleRequest request);

    ItemCategoryLocaleEntity getEntityById(Long id, ItemCategoryEntity itemCategoryEntity);

    SuccessResponse update(ItemCategoryLocaleEntity entity, UpdateItemCategoryLocaleRequest request);

    SuccessResponse delete(ItemCategoryLocaleEntity entity);
}
