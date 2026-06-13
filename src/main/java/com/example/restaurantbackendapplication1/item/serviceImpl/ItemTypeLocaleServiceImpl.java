package com.example.restaurantbackendapplication1.item.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.item.dto.request.itemtypelocale.CreateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemtypelocale.UpdateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.item.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemTypeLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.item.model.mapper.ItemTypeLocaleMapper;
import com.example.restaurantbackendapplication1.item.repository.ItemTypeLocaleRepository;
import com.example.restaurantbackendapplication1.item.service.ItemTypeLocaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ItemTypeLocaleServiceImpl implements ItemTypeLocaleService {

    private final ItemTypeLocaleRepository itemTypeLocaleRepository;

    public ItemTypeLocaleServiceImpl(ItemTypeLocaleRepository itemTypeLocaleRepository) {
        this.itemTypeLocaleRepository = itemTypeLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(ItemTypeEntity itemTypeEntity,
                                  LocaleEntity localeEntity,
                                  CreateItemTypeLocaleRequest request) {
        ItemTypeLocaleEntity entity = ItemTypeLocaleMapper.create(request, itemTypeEntity, localeEntity);
        itemTypeLocaleRepository.save(entity);
        log.info("ItemTypeLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse update(ItemTypeLocaleEntity entity, UpdateItemTypeLocaleRequest request) {
        ItemTypeLocaleMapper.update(entity, request);
        itemTypeLocaleRepository.save(entity);
        log.info("ItemTypeLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(ItemTypeLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        itemTypeLocaleRepository.save(entity);
        log.info("ItemTypeLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public ItemTypeLocaleEntity getEntityById(Long itemTypeId, Long id) {
        return itemTypeLocaleRepository
                .findByItemTypeEntity_IdAndIdAndIsActiveAndIsDeleted(itemTypeId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("ItemTypeLocale not found with id: " + id));
    }
}
