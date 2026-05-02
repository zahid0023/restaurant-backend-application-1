package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.itemlocale.CreateItemLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemlocale.UpdateItemLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.ItemLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.ItemLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.ItemLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.ItemLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.ItemLocaleSummary;
import com.example.restaurantbackendapplication1.repository.ItemLocaleRepository;
import com.example.restaurantbackendapplication1.service.ItemLocaleService;
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
public class ItemLocaleServiceImpl implements ItemLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = ItemLocaleSortField.allowedFields();

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
    public ItemLocaleResponse getById(Long id, ItemEntity itemEntity) {
        ItemLocaleEntity entity = getEntityById(id, itemEntity);
        ItemLocaleDto dto = ItemLocaleMapper.toDto(entity);
        return new ItemLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<ItemLocaleSummary> getAll(ItemEntity itemEntity, PaginatedRequest request) {
        Page<@NonNull ItemLocaleSummary> page = itemLocaleRepository
                .findAllByItemEntityAndIsActiveAndIsDeleted(
                        itemEntity, true, false, request.toPageable(ALLOWED_SORT_FIELDS)
                );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(ItemLocaleEntity entity, LocaleEntity locale, UpdateItemLocaleRequest request) {
        ItemLocaleMapper.update(entity, request, locale);
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

    @Override
    public ItemLocaleEntity getEntityById(Long id, ItemEntity itemEntity) {
        return itemLocaleRepository
                .findByIdAndItemEntityAndIsActiveAndIsDeleted(id, itemEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("ItemLocale not found with id: " + id));
    }
}
