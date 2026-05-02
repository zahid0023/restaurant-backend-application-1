package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.itemtypelocale.CreateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemtypelocale.UpdateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.ItemTypeLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.ItemTypeLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.ItemTypeLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.ItemTypeLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.ItemTypeLocaleSummary;
import com.example.restaurantbackendapplication1.repository.ItemTypeLocaleRepository;
import com.example.restaurantbackendapplication1.service.ItemTypeLocaleService;
import com.example.restaurantbackendapplication1.utils.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Slf4j
public class ItemTypeLocaleServiceImpl implements ItemTypeLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = ItemTypeLocaleSortField.allowedFields();

    private final ItemTypeLocaleRepository itemTypeLocaleRepository;

    public ItemTypeLocaleServiceImpl(ItemTypeLocaleRepository itemTypeLocaleRepository) {
        this.itemTypeLocaleRepository = itemTypeLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(ItemTypeEntity itemType, LocaleEntity locale, CreateItemTypeLocaleRequest request) {
        ItemTypeLocaleEntity entity = ItemTypeLocaleMapper.fromRequest(request, itemType, locale);
        itemTypeLocaleRepository.save(entity);
        log.info("ItemTypeLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public ItemTypeLocaleResponse getById(Long id, ItemTypeEntity itemType) {
        ItemTypeLocaleEntity entity = getEntityById(id, itemType);
        ItemTypeLocaleDto dto = ItemTypeLocaleMapper.toDto(entity);
        return new ItemTypeLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<ItemTypeLocaleSummary> getAll(ItemTypeEntity itemType, PaginatedRequest request) {
        Page<@NonNull ItemTypeLocaleSummary> page = itemTypeLocaleRepository
                .findAllByItemTypeEntityAndIsActiveAndIsDeleted(
                        itemType, true, false, request.toPageable(ALLOWED_SORT_FIELDS)
                );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(ItemTypeLocaleEntity entity, LocaleEntity locale, UpdateItemTypeLocaleRequest request) {
        ItemTypeLocaleMapper.update(entity, request, locale);
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
    public ItemTypeLocaleEntity getEntityById(Long id, ItemTypeEntity itemType) {
        return itemTypeLocaleRepository
                .findByIdAndItemTypeEntityAndIsActiveAndIsDeleted(id, itemType, true, false)
                .orElseThrow(() -> new EntityNotFoundException("ItemTypeLocale not found with id: " + id));
    }
}
