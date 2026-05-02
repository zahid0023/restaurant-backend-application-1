package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.recipe.CreateRecipeRequest;
import com.example.restaurantbackendapplication1.dto.request.recipe.UpdateRecipeRequest;
import com.example.restaurantbackendapplication1.dto.response.RecipeResponse;
import com.example.restaurantbackendapplication1.model.dto.RecipeDto;
import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.RecipeEntity;
import com.example.restaurantbackendapplication1.model.enums.RecipeSortField;
import com.example.restaurantbackendapplication1.model.mapper.RecipeMapper;
import com.example.restaurantbackendapplication1.model.projection.RecipeSummary;
import com.example.restaurantbackendapplication1.repository.RecipeRepository;
import com.example.restaurantbackendapplication1.service.RecipeService;
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
public class RecipeServiceImpl implements RecipeService {

    private static final Set<String> ALLOWED_SORT_FIELDS = RecipeSortField.allowedFields();

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(MenuItemEntity menuItemEntity, CreateRecipeRequest request) {
        RecipeEntity entity = RecipeMapper.fromRequest(request, menuItemEntity);
        recipeRepository.save(entity);
        log.info("Recipe created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public RecipeEntity getEntityById(Long id, MenuItemEntity menuItemEntity) {
        return recipeRepository.findByIdAndMenuItemEntityAndIsActiveAndIsDeleted(id, menuItemEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + id));
    }

    @Override
    public RecipeResponse getById(Long id, MenuItemEntity menuItemEntity) {
        RecipeEntity entity = getEntityById(id, menuItemEntity);
        RecipeDto dto = RecipeMapper.toDto(entity);
        return new RecipeResponse(dto);
    }

    @Override
    public PaginatedResponse<RecipeSummary> getAll(MenuItemEntity menuItemEntity, PaginatedRequest request) {
        Page<@NonNull RecipeSummary> page = recipeRepository.findAllByMenuItemEntityAndIsActiveAndIsDeleted(
                menuItemEntity, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(RecipeEntity entity, UpdateRecipeRequest request) {
        RecipeMapper.update(entity, request);
        recipeRepository.save(entity);
        log.info("Recipe updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(RecipeEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        recipeRepository.save(entity);
        log.info("Recipe soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
