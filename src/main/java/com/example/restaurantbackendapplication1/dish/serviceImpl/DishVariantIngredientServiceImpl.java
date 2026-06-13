package com.example.restaurantbackendapplication1.dish.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantingredient.CreateDishVariantIngredientRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantingredient.UpdateDishVariantIngredientRequest;
import com.example.restaurantbackendapplication1.dish.dto.response.DishVariantIngredientResponse;
import com.example.restaurantbackendapplication1.dish.model.dto.DishVariantIngredientDto;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantIngredientEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.dish.model.enums.DishVariantIngredientSortField;
import com.example.restaurantbackendapplication1.dish.model.mapper.DishVariantIngredientMapper;
import com.example.restaurantbackendapplication1.dish.model.projection.DishVariantIngredientSummary;
import com.example.restaurantbackendapplication1.dish.repository.DishVariantIngredientRepository;
import com.example.restaurantbackendapplication1.dish.service.DishVariantIngredientService;
import com.example.restaurantbackendapplication1.commons.utils.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Slf4j
public class DishVariantIngredientServiceImpl implements DishVariantIngredientService {

    private static final Set<String> ALLOWED_SORT_FIELDS = DishVariantIngredientSortField.allowedFields();

    private final DishVariantIngredientRepository dishVariantIngredientRepository;

    public DishVariantIngredientServiceImpl(DishVariantIngredientRepository dishVariantIngredientRepository) {
        this.dishVariantIngredientRepository = dishVariantIngredientRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(DishVariantEntity dishVariantEntity,
                                  ItemEntity itemEntity,
                                  UnitEntity unitEntity,
                                  CreateDishVariantIngredientRequest request) {
        DishVariantIngredientEntity entity = DishVariantIngredientMapper.create(request, dishVariantEntity, itemEntity, unitEntity);
        dishVariantIngredientRepository.save(entity);
        log.info("DishVariantIngredient created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DishVariantIngredientEntity getEntityById(Long dishVariantId, Long id) {
        return dishVariantIngredientRepository
                .findByDishVariantEntity_IdAndIdAndIsActiveAndIsDeleted(dishVariantId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("DishVariantIngredient not found with id: " + id));
    }

    @Override
    public DishVariantIngredientResponse getById(Long dishVariantId, Long id) {
        DishVariantIngredientEntity entity = getEntityById(dishVariantId, id);
        DishVariantIngredientDto dto = DishVariantIngredientMapper.toDto(entity);
        return new DishVariantIngredientResponse(dto);
    }

    @Override
    public PaginatedResponse<DishVariantIngredientSummary> getAll(Long dishVariantId, PaginatedRequest request) {
        Page<@NonNull DishVariantIngredientSummary> page = dishVariantIngredientRepository
                .findAllByDishVariantEntity_IdAndIsActiveAndIsDeleted(dishVariantId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(DishVariantIngredientEntity entity,
                                  UpdateDishVariantIngredientRequest request,
                                  UnitEntity unitEntity) {
        DishVariantIngredientMapper.update(entity, request, unitEntity);
        dishVariantIngredientRepository.save(entity);
        log.info("DishVariantIngredient updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(DishVariantIngredientEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        dishVariantIngredientRepository.save(entity);
        log.info("DishVariantIngredient soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
