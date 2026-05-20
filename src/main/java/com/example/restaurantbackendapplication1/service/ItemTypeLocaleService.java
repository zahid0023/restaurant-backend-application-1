package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.itemtypelocale.CreateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemtypelocale.UpdateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;

public interface ItemTypeLocaleService {
    SuccessResponse create(ItemTypeEntity itemTypeEntity,
                           LocaleEntity localeEntity,
                           CreateItemTypeLocaleRequest request);

    ItemTypeLocaleEntity getEntityById(Long itemTypeId, Long id);

    SuccessResponse update(ItemTypeLocaleEntity entity,
                           UpdateItemTypeLocaleRequest request);

    SuccessResponse delete(ItemTypeLocaleEntity entity);
}
