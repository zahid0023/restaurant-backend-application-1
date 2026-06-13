package com.example.restaurantbackendapplication1.item.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.item.dto.request.itemlocale.CreateItemLocaleRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemlocale.UpdateItemLocaleRequest;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.item.model.mapper.ItemLocaleMapper;
import com.example.restaurantbackendapplication1.item.repository.ItemLocaleRepository;
import com.example.restaurantbackendapplication1.item.service.ItemLocaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ItemLocaleServiceImpl implements ItemLocaleService {

    private final ItemLocaleRepository itemLocaleRepository;

    public ItemLocaleServiceImpl(ItemLocaleRepository itemLocaleRepository) {
        this.itemLocaleRepository = itemLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(ItemEntity itemEntity, LocaleEntity locale, CreateItemLocaleRequest request) {
        ItemLocaleEntity entity = ItemLocaleMapper.fromRequest(request, itemEntity, locale);
        itemLocaleRepository.save(entity);
        log.info("ItemLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public ItemLocaleEntity getEntityById(Long id, ItemEntity itemEntity) {
        return itemLocaleRepository
                .findByIdAndItemEntityAndIsActiveAndIsDeleted(id, itemEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("ItemLocale not found with id: " + id));
    }

    @Transactional
    @Override
    public SuccessResponse update(ItemLocaleEntity entity, UpdateItemLocaleRequest request) {
        ItemLocaleMapper.update(entity, request);
        itemLocaleRepository.save(entity);
        log.info("ItemLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(ItemLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        itemLocaleRepository.save(entity);
        log.info("ItemLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
