package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.enums.ItemItemCategorySortField;
import com.example.restaurantbackendapplication1.model.mapper.ItemItemCategoryMapper;
import com.example.restaurantbackendapplication1.model.projection.ItemSummary;
import com.example.restaurantbackendapplication1.repository.ItemItemCategoryRepository;
import com.example.restaurantbackendapplication1.service.ItemItemCategoryService;
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
public class ItemItemCategoryServiceImpl implements ItemItemCategoryService {

    private static final Set<String> ALLOWED_SORT_FIELDS = ItemItemCategorySortField.allowedFields();

    private final ItemItemCategoryRepository itemItemCategoryRepository;

    public ItemItemCategoryServiceImpl(ItemItemCategoryRepository itemItemCategoryRepository) {
        this.itemItemCategoryRepository = itemItemCategoryRepository;
    }

    @Transactional
    @Override
    public SuccessResponse assign(ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity) {
        boolean alreadyAssigned = itemItemCategoryRepository.existsByItemCategoryEntityAndItemEntityAndIsActiveAndIsDeleted(itemCategoryEntity, itemEntity, true, false);
        if (alreadyAssigned) {
            throw new IllegalStateException(
                    "PlatformItem " + itemEntity.getId() + " is already assigned to PlatformItemCategory " + itemCategoryEntity.getId());
        }
        ItemItemCategoryEntity entity = ItemItemCategoryMapper.fromRequest(itemEntity, itemCategoryEntity);
        itemItemCategoryRepository.save(entity);
        log.info("PlatformItem {} assigned to PlatformItemCategory {}", itemEntity.getId(), itemCategoryEntity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public PaginatedResponse<ItemSummary> getAllItems(Long itemCategoryId, PaginatedRequest request) {
        Page<@NonNull ItemSummary> page = itemItemCategoryRepository.findAllItemsByItemCategoryId(itemCategoryId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Override
    public ItemItemCategoryEntity getAssignment(ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity) {
        return itemItemCategoryRepository.findByItemCategoryEntityAndItemEntityAndIsActiveAndIsDeleted(itemCategoryEntity, itemEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException(
                        "PlatformItem " + itemEntity.getId() + " is not assigned to PlatformItemCategory " + itemCategoryEntity.getId()));
    }

    @Transactional
    @Override
    public SuccessResponse unassign(ItemItemCategoryEntity itemItemCategoryEntity) {
        itemItemCategoryEntity.setIsActive(false);
        itemItemCategoryEntity.setIsDeleted(false);
        itemItemCategoryRepository.save(itemItemCategoryEntity);
        log.info("PlatformItem {} unassigned from PlatformItemCategory {}",
                itemItemCategoryEntity.getItemCategoryEntity().getId(),
                itemItemCategoryEntity.getItemEntity().getId());
        return new SuccessResponse(true, itemItemCategoryEntity.getId());
    }
}
