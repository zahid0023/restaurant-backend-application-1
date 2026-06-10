package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.itemcategory.CreateItemCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategory.UpdateItemCategoryRequest;
import com.example.restaurantbackendapplication1.dto.response.ItemCategoryResponse;
import com.example.restaurantbackendapplication1.model.dto.ItemCategoryDto;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.ItemCategorySortField;
import com.example.restaurantbackendapplication1.model.mapper.ItemCategoryMapper;
import com.example.restaurantbackendapplication1.model.projection.ItemCategorySummary;
import com.example.restaurantbackendapplication1.repository.ItemCategoryRepository;
import com.example.restaurantbackendapplication1.service.ItemCategoryService;
import com.example.restaurantbackendapplication1.utils.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class ItemCategoryServiceImpl implements ItemCategoryService {

    private static final Set<String> ALLOWED_SORT_FIELDS = ItemCategorySortField.allowedFields();

    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryServiceImpl(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateItemCategoryRequest request,
                                  ItemCategoryEntity itemCategoryEntity,
                                  Map<Long, LocaleEntity> localeEntityMap) {
        ItemCategoryEntity entity = ItemCategoryMapper.fromRequest(request, itemCategoryEntity, localeEntityMap);
        itemCategoryRepository.save(entity);
        log.info("ItemCategory created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public ItemCategoryResponse getById(Long id) {
        ItemCategoryEntity entity = getEntityById(id);
        ItemCategoryDto dto = ItemCategoryMapper.toDto(entity);
        return new ItemCategoryResponse(dto);
    }

    @Override
    public PaginatedResponse<ItemCategorySummary> getAllRoots(PaginatedRequest request) {
        Page<@NonNull ItemCategorySummary> page = itemCategoryRepository.findAllByItemCategoryEntityIsNullAndIsActiveAndIsDeleted(true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Override
    public PaginatedResponse<ItemCategorySummary> getAllSubCategories(Long itemCategoryId, PaginatedRequest request) {
        Page<@NonNull ItemCategorySummary> page = itemCategoryRepository.findAllByItemCategoryEntity_IdAndIsActiveAndIsDeleted(itemCategoryId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(ItemCategoryEntity entity,
                                  UpdateItemCategoryRequest request) {
        ItemCategoryMapper.update(entity, request);
        itemCategoryRepository.save(entity);
        log.info("ItemCategory updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(ItemCategoryEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        itemCategoryRepository.save(entity);
        log.info("ItemCategory soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public ItemCategoryEntity getEntityById(Long id) {
        return itemCategoryRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("ItemCategoryEntity with id: " + id));
    }
}
