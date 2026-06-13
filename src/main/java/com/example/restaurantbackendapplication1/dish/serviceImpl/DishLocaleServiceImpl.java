package com.example.restaurantbackendapplication1.dish.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dish.dto.request.dishlocale.CreateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishlocale.UpdateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dish.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.dish.model.mapper.DishLocaleMapper;
import com.example.restaurantbackendapplication1.dish.repository.DishLocaleRepository;
import com.example.restaurantbackendapplication1.dish.service.DishLocaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DishLocaleServiceImpl implements DishLocaleService {

    private final DishLocaleRepository dishLocaleRepository;

    public DishLocaleServiceImpl(DishLocaleRepository dishLocaleRepository) {
        this.dishLocaleRepository = dishLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(DishEntity dishEntity,
                                  LocaleEntity localeEntity,
                                  CreateDishLocaleRequest request) {
        DishLocaleEntity entity = DishLocaleMapper.create(request, dishEntity, localeEntity);
        dishLocaleRepository.save(entity);
        log.info("DishLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DishLocaleEntity getEntityById(Long dishId, Long id) {
        return dishLocaleRepository.findByDishEntity_IdAndIdAndIsActiveAndIsDeleted(dishId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Dish locale not found with id: " + id));
    }

    @Transactional
    @Override
    public SuccessResponse update(DishLocaleEntity entity, UpdateDishLocaleRequest request) {
        DishLocaleMapper.update(entity, request);
        dishLocaleRepository.save(entity);
        log.info("DishLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(DishLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        dishLocaleRepository.save(entity);
        log.info("DishLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
