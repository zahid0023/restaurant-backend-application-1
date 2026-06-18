package com.example.restaurantbackendapplication1.item.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.item.dto.request.itemtype.CreateItemTypeRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemtype.UpdateItemTypeRequest;
import com.example.restaurantbackendapplication1.item.dto.response.ItemTypeResponse;
import com.example.restaurantbackendapplication1.item.model.dto.ItemTypeDto;
import com.example.restaurantbackendapplication1.item.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.item.model.enums.ItemTypeSortField;
import com.example.restaurantbackendapplication1.item.model.mapper.ItemTypeMapper;
import com.example.restaurantbackendapplication1.item.model.projection.ItemTypeSummary;
import com.example.restaurantbackendapplication1.item.repository.ItemTypeRepository;
import com.example.restaurantbackendapplication1.item.service.ItemTypeService;
import com.example.restaurantbackendapplication1.commons.utils.Pagination;
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
public class ItemTypeServiceImpl implements ItemTypeService {

    private static final Set<String> ALLOWED_SORT_FIELDS = ItemTypeSortField.allowedFields();

    private final ItemTypeRepository itemTypeRepository;

    public ItemTypeServiceImpl(ItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateItemTypeRequest request, Map<Long, LocaleEntity> localeEntityMap) {
        ItemTypeEntity entity = ItemTypeMapper.create(request, localeEntityMap);
        itemTypeRepository.save(entity);
        log.info("ItemType created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public ItemTypeResponse getById(Long id) {
        ItemTypeEntity entity = getEntityById(id);
        ItemTypeDto dto = ItemTypeMapper.toDto(entity, false);
        return new ItemTypeResponse(dto);
    }

    @Override
    public PaginatedResponse<ItemTypeSummary> getAll(PaginatedRequest request) {
        Page<@NonNull ItemTypeSummary> page = itemTypeRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS)
        );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(ItemTypeEntity entity, UpdateItemTypeRequest request) {
        ItemTypeMapper.update(entity, request);
        itemTypeRepository.save(entity);
        log.info("ItemType updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long id) {
        ItemTypeEntity entity = getEntityById(id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        itemTypeRepository.save(entity);
        log.info("ItemType soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public ItemTypeEntity getEntityById(Long id) {
        return itemTypeRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("ItemType not found with id: " + id));
    }
}
