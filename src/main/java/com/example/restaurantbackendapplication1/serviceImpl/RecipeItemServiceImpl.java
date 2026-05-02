package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.recipeitem.CreateRecipeItemRequest;
import com.example.restaurantbackendapplication1.dto.request.recipeitem.UpdateRecipeItemRequest;
import com.example.restaurantbackendapplication1.dto.response.RecipeItemResponse;
import com.example.restaurantbackendapplication1.model.dto.RecipeItemDto;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.RecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.RecipeItemEntity;
import com.example.restaurantbackendapplication1.model.enums.RecipeItemSortField;
import com.example.restaurantbackendapplication1.model.mapper.RecipeItemMapper;
import com.example.restaurantbackendapplication1.model.projection.RecipeItemSummary;
import com.example.restaurantbackendapplication1.repository.RecipeItemRepository;
import com.example.restaurantbackendapplication1.service.RecipeItemService;
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
public class RecipeItemServiceImpl implements RecipeItemService {

    private static final Set<String> ALLOWED_SORT_FIELDS = RecipeItemSortField.allowedFields();

    private final RecipeItemRepository recipeItemRepository;

    public RecipeItemServiceImpl(RecipeItemRepository recipeItemRepository) {
        this.recipeItemRepository = recipeItemRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(RecipeEntity recipeEntity, ItemEntity itemEntity,
                                  CreateRecipeItemRequest request) {
        RecipeItemEntity entity = RecipeItemMapper.fromRequest(request, recipeEntity, itemEntity);
        recipeItemRepository.save(entity);
        log.info("RecipeItem created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public RecipeItemEntity getEntityById(Long id, RecipeEntity recipeEntity) {
        return recipeItemRepository.findByIdAndRecipeEntityAndIsActiveAndIsDeleted(id, recipeEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("RecipeItem not found with id: " + id));
    }

    @Override
    public RecipeItemResponse getById(Long id, RecipeEntity recipeEntity) {
        RecipeItemEntity entity = getEntityById(id, recipeEntity);
        RecipeItemDto dto = RecipeItemMapper.toDto(entity);
        return new RecipeItemResponse(dto);
    }

    @Override
    public PaginatedResponse<RecipeItemSummary> getAll(RecipeEntity recipeEntity, PaginatedRequest request) {
        Page<@NonNull RecipeItemSummary> page = recipeItemRepository.findAllByRecipeEntityAndIsActiveAndIsDeleted(
                recipeEntity, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(RecipeItemEntity entity, ItemEntity itemEntity,
                                  UpdateRecipeItemRequest request) {
        RecipeItemMapper.update(entity, request, itemEntity);
        recipeItemRepository.save(entity);
        log.info("RecipeItem updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(RecipeItemEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        recipeItemRepository.save(entity);
        log.info("RecipeItem soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
