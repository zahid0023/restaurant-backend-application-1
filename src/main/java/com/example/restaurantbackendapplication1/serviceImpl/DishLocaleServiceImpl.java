package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishlocale.CreateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishlocale.UpdateDishLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishesLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.mapper.DishLocaleMapper;
import com.example.restaurantbackendapplication1.repository.DishLocaleRepository;
import com.example.restaurantbackendapplication1.service.DishLocaleService;
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
        DishesLocaleEntity entity = DishLocaleMapper.create(request, dishEntity, localeEntity);
        dishLocaleRepository.save(entity);
        log.info("DishLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DishesLocaleEntity getEntityById(Long dishId, Long id) {
        return dishLocaleRepository.findByDishEntity_IdAndIdAndIsActiveAndIsDeleted(dishId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Dish locale not found with id: " + id));
    }

    @Transactional
    @Override
    public SuccessResponse update(DishesLocaleEntity entity, UpdateDishLocaleRequest request) {
        DishLocaleMapper.update(entity, request);
        dishLocaleRepository.save(entity);
        log.info("DishLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(DishesLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        dishLocaleRepository.save(entity);
        log.info("DishLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
