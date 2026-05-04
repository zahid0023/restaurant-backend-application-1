package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.CreateDishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.UpdateDishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.dto.response.DishRecipeIngredientResponse;
import com.example.restaurantbackendapplication1.model.dto.DishRecipeIngredientDto;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeIngredientEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.enums.DishRecipeIngredientSortField;
import com.example.restaurantbackendapplication1.model.mapper.DishRecipeIngredientMapper;
import com.example.restaurantbackendapplication1.model.projection.DishRecipeIngredientSummary;
import com.example.restaurantbackendapplication1.repository.DishRecipeIngredientRepository;
import com.example.restaurantbackendapplication1.service.DishRecipeIngredientService;
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
public class DishRecipeIngredientServiceImpl implements DishRecipeIngredientService {

    private static final Set<String> ALLOWED_SORT_FIELDS = DishRecipeIngredientSortField.allowedFields();

    private final DishRecipeIngredientRepository dishRecipeIngredientRepository;

    public DishRecipeIngredientServiceImpl(DishRecipeIngredientRepository dishRecipeIngredientRepository) {
        this.dishRecipeIngredientRepository = dishRecipeIngredientRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateDishRecipeIngredientRequest request,
                                  DishRecipeEntity dishRecipeEntity,
                                  ItemEntity itemEntity) {
        DishRecipeIngredientEntity entity = DishRecipeIngredientMapper.fromRequest(request, dishRecipeEntity, itemEntity);
        dishRecipeIngredientRepository.save(entity);
        log.info("DishRecipeIngredient created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DishRecipeIngredientEntity getEntityById(Long dishRecipeId, Long id) {
        return dishRecipeIngredientRepository.findByDishRecipeEntity_IdAndIdAndIsActiveAndIsDeleted(dishRecipeId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("DishRecipeIngredient not found with id: " + id));
    }

    @Override
    public DishRecipeIngredientResponse getById(Long dishRecipeId, Long id) {
        DishRecipeIngredientEntity entity = getEntityById(dishRecipeId, id);
        DishRecipeIngredientDto dto = DishRecipeIngredientMapper.toDto(entity);
        return new DishRecipeIngredientResponse(dto);
    }

    @Override
    public PaginatedResponse<DishRecipeIngredientSummary> getAll(Long dishRecipeId, PaginatedRequest request) {
        Page<@NonNull DishRecipeIngredientSummary> page = dishRecipeIngredientRepository
                .findAllByDishRecipeEntity_IdAndIsActiveAndIsDeleted(dishRecipeId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(DishRecipeIngredientEntity entity,
                                  ItemEntity itemEntity,
                                  UpdateDishRecipeIngredientRequest request) {
        DishRecipeIngredientMapper.update(entity, request, itemEntity);
        dishRecipeIngredientRepository.save(entity);
        log.info("DishRecipeIngredient updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(DishRecipeIngredientEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        dishRecipeIngredientRepository.save(entity);
        log.info("DishRecipeIngredient soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
