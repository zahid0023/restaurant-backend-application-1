package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.item.CreateItemRequest;
import com.example.restaurantbackendapplication1.dto.request.item.UpdateItemRequest;
import com.example.restaurantbackendapplication1.dto.response.ItemResponse;
import com.example.restaurantbackendapplication1.model.dto.ItemDto;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.model.enums.ItemSortField;
import com.example.restaurantbackendapplication1.model.mapper.ItemMapper;
import com.example.restaurantbackendapplication1.model.projection.ItemSummary;
import com.example.restaurantbackendapplication1.repository.ItemRepository;
import com.example.restaurantbackendapplication1.service.ItemService;
import com.example.restaurantbackendapplication1.utils.EntityValidator;
import com.example.restaurantbackendapplication1.utils.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    private static final Set<String> ALLOWED_SORT_FIELDS = ItemSortField.allowedFields();

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateItemRequest request,
                                  UnitEntity unitEntity,
                                  Map<Long, LocaleEntity> localeEntityMap) {
        ItemEntity entity = ItemMapper.fromRequest(request, unitEntity, localeEntityMap);
        itemRepository.save(entity);
        log.info("Item created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public ItemEntity getEntityById(Long id) {
        return itemRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + id));
    }

    @Override
    public List<ItemEntity> getAll(Set<Long> ids) {
        List<ItemEntity> entities = itemRepository.findAllByIdInAndIsActiveAndIsDeleted(ids, true, false);
        EntityValidator.validateAllFound(ids, entities, ItemEntity::getId, "Item");
        return entities;
    }

    @Override
    public ItemResponse getById(Long id) {
        ItemEntity entity = getEntityById(id);
        ItemDto dto = ItemMapper.toDto(entity);
        return new ItemResponse(dto);
    }

    @Override
    public PaginatedResponse<ItemSummary> getAll(PaginatedRequest request) {
        Page<@NonNull ItemSummary> page = itemRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS)
        );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(ItemEntity entity,
                                  UpdateItemRequest request,
                                  UnitEntity unitEntity) {
        ItemMapper.update(entity, request, unitEntity);
        itemRepository.save(entity);
        log.info("Item updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long id) {
        ItemEntity entity = getEntityById(id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        itemRepository.save(entity);
        log.info("Item soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
