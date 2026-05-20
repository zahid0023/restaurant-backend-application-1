package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishrecipe.CreateDishRecipeRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipe.UpdateDishRecipeRequest;
import com.example.restaurantbackendapplication1.dto.response.DishRecipeResponse;
import com.example.restaurantbackendapplication1.model.dto.DishRecipeDto;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.model.enums.DishRecipeSortField;
import com.example.restaurantbackendapplication1.model.mapper.DishRecipeMapper;
import com.example.restaurantbackendapplication1.model.projection.DishRecipeSummary;
import com.example.restaurantbackendapplication1.repository.DishRecipeRepository;
import com.example.restaurantbackendapplication1.service.DishRecipeService;
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
public class DishRecipeServiceImpl implements DishRecipeService {

    private static final Set<String> ALLOWED_SORT_FIELDS = DishRecipeSortField.allowedFields();

    private final DishRecipeRepository dishRecipeRepository;

    public DishRecipeServiceImpl(DishRecipeRepository dishRecipeRepository) {
        this.dishRecipeRepository = dishRecipeRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateDishRecipeRequest request,
                                  DishVariantEntity dishVariantEntity,
                                  Map<Long, ItemEntity> itemEntityMap,
                                  Map<Long, UnitEntity> unitEntityMap) {
        DishRecipeEntity entity = DishRecipeMapper.create(request, dishVariantEntity, itemEntityMap, unitEntityMap);
        dishRecipeRepository.save(entity);
        log.info("DishRecipe created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DishRecipeEntity getEntityById(Long dishVariantId, Long id) {
        return dishRecipeRepository.findByDishVariantEntity_IdAndIdAndIsActiveAndIsDeleted(dishVariantId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("DishRecipe not found with id: " + id));
    }

    @Override
    public DishRecipeResponse getById(Long dishVariantId, Long id) {
        DishRecipeEntity entity = getEntityById(dishVariantId, id);
        DishRecipeDto dto = DishRecipeMapper.toDto(entity);
        return new DishRecipeResponse(dto);
    }

    @Override
    public PaginatedResponse<DishRecipeSummary> getAll(Long dishVariantId, PaginatedRequest request) {
        Page<@NonNull DishRecipeSummary> page = dishRecipeRepository
                .findAllByDishVariantEntity_IdAndIsActiveAndIsDeleted(dishVariantId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(DishRecipeEntity entity, UpdateDishRecipeRequest request) {
        DishRecipeMapper.update(entity, request);
        dishRecipeRepository.save(entity);
        log.info("DishRecipe updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long dishVariantId, Long id) {
        DishRecipeEntity entity = getEntityById(dishVariantId, id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        dishRecipeRepository.save(entity);
        log.info("DishRecipe soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
