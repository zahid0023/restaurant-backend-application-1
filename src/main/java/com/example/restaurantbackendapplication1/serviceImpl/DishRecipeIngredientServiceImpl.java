package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.CreateDishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.UpdateDishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeIngredientEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.model.mapper.DishRecipeIngredientMapper;
import com.example.restaurantbackendapplication1.repository.DishRecipeIngredientRepository;
import com.example.restaurantbackendapplication1.service.DishRecipeIngredientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DishRecipeIngredientServiceImpl implements DishRecipeIngredientService {

    private final DishRecipeIngredientRepository dishRecipeIngredientRepository;

    public DishRecipeIngredientServiceImpl(DishRecipeIngredientRepository dishRecipeIngredientRepository) {
        this.dishRecipeIngredientRepository = dishRecipeIngredientRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateDishRecipeIngredientRequest request,
                                  DishRecipeEntity dishRecipeEntity,
                                  ItemEntity itemEntity,
                                  UnitEntity unitEntity) {
        DishRecipeIngredientEntity entity = DishRecipeIngredientMapper.create(request, dishRecipeEntity, itemEntity, unitEntity);
        dishRecipeIngredientRepository.save(entity);
        log.info("DishRecipeIngredient created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DishRecipeIngredientEntity getEntityById(Long dishRecipeId, Long id) {
        return dishRecipeIngredientRepository.findByDishRecipeEntity_IdAndIdAndIsActiveAndIsDeleted(dishRecipeId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("DishRecipeIngredient not found with id: " + id));
    }

    @Transactional
    @Override
    public SuccessResponse update(DishRecipeIngredientEntity entity,
                                  ItemEntity itemEntity,
                                  UnitEntity unitEntity,
                                  UpdateDishRecipeIngredientRequest request) {
        DishRecipeIngredientMapper.update(entity, request, itemEntity, unitEntity);
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
