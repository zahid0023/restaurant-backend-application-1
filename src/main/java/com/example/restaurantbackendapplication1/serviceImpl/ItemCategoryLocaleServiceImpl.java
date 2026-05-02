package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.CreateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.UpdateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.ItemCategoryLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.ItemCategoryLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.ItemCategoryLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.ItemCategoryLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.ItemCategoryLocaleSummary;
import com.example.restaurantbackendapplication1.repository.ItemCategoryLocaleRepository;
import com.example.restaurantbackendapplication1.service.ItemCategoryLocaleService;
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
public class ItemCategoryLocaleServiceImpl implements ItemCategoryLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = ItemCategoryLocaleSortField.allowedFields();

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

    @Override
    public ItemCategoryLocaleResponse getById(Long id, ItemCategoryEntity itemCategoryEntity) {
        ItemCategoryLocaleEntity entity = getEntityById(id, itemCategoryEntity);
        ItemCategoryLocaleDto dto = ItemCategoryLocaleMapper.toDto(entity);
        return new ItemCategoryLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<ItemCategoryLocaleSummary> getAll(ItemCategoryEntity itemCategoryEntity, PaginatedRequest request) {
        Page<@NonNull ItemCategoryLocaleSummary> page = itemCategoryLocaleRepository
                .findAllByItemCategoryEntityAndIsActiveAndIsDeleted(
                        itemCategoryEntity, true, false, request.toPageable(ALLOWED_SORT_FIELDS)
                );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(ItemCategoryLocaleEntity entity, LocaleEntity locale, UpdateItemCategoryLocaleRequest request) {
        ItemCategoryLocaleMapper.update(entity, request, locale);
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
