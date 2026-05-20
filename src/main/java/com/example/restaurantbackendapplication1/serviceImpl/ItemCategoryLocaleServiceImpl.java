package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.CreateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.UpdateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.mapper.ItemCategoryLocaleMapper;
import com.example.restaurantbackendapplication1.repository.ItemCategoryLocaleRepository;
import com.example.restaurantbackendapplication1.service.ItemCategoryLocaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ItemCategoryLocaleServiceImpl implements ItemCategoryLocaleService {

    private final ItemCategoryLocaleRepository itemCategoryLocaleRepository;

    public ItemCategoryLocaleServiceImpl(ItemCategoryLocaleRepository itemCategoryLocaleRepository) {
        this.itemCategoryLocaleRepository = itemCategoryLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(ItemCategoryEntity itemCategoryEntity, LocaleEntity locale, CreateItemCategoryLocaleRequest request) {
        ItemCategoryLocaleEntity entity = ItemCategoryLocaleMapper.fromRequest(request, itemCategoryEntity, locale);
        itemCategoryLocaleRepository.save(entity);
        log.info("ItemCategoryLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse update(ItemCategoryLocaleEntity entity, UpdateItemCategoryLocaleRequest request) {
        ItemCategoryLocaleMapper.update(entity, request);
        itemCategoryLocaleRepository.save(entity);
        log.info("ItemCategoryLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(ItemCategoryLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        itemCategoryLocaleRepository.save(entity);
        log.info("ItemCategoryLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public ItemCategoryLocaleEntity getEntityById(Long id, ItemCategoryEntity itemCategoryEntity) {
        return itemCategoryLocaleRepository
                .findByIdAndItemCategoryEntityAndIsActiveAndIsDeleted(id, itemCategoryEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("ItemCategoryLocale not found with id: " + id));
    }
}
