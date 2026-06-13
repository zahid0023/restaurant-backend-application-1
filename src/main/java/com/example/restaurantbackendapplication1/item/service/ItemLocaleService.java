package com.example.restaurantbackendapplication1.item.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.item.dto.request.itemlocale.CreateItemLocaleRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemlocale.UpdateItemLocaleRequest;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;

public interface ItemLocaleService {
    SuccessResponse create(ItemEntity itemEntity, LocaleEntity locale, CreateItemLocaleRequest request);

    ItemLocaleEntity getEntityById(Long id, ItemEntity itemEntity);

    SuccessResponse update(ItemLocaleEntity entity, UpdateItemLocaleRequest request);

    SuccessResponse delete(ItemLocaleEntity entity);
}
