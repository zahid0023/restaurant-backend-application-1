package com.example.restaurantbackendapplication1.dish.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantlocale.CreateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantlocale.UpdateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.dish.model.mapper.DishVariantLocaleMapper;
import com.example.restaurantbackendapplication1.dish.repository.DishVariantLocaleRepository;
import com.example.restaurantbackendapplication1.dish.service.DishVariantLocaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DishVariantLocaleServiceImpl implements DishVariantLocaleService {

    private final DishVariantLocaleRepository dishVariantLocaleRepository;

    public DishVariantLocaleServiceImpl(DishVariantLocaleRepository dishVariantLocaleRepository) {
        this.dishVariantLocaleRepository = dishVariantLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(DishVariantEntity dishVariantEntity,
                                  LocaleEntity localeEntity,
                                  CreateDishVariantLocaleRequest request) {
        DishVariantLocaleEntity entity = DishVariantLocaleMapper.create(request, dishVariantEntity, localeEntity);
        dishVariantLocaleRepository.save(entity);
        log.info("DishVariantLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DishVariantLocaleEntity getEntityById(Long dishVariantId, Long id) {
        return dishVariantLocaleRepository
                .findByDishVariantEntity_IdAndIdAndIsActiveAndIsDeleted(dishVariantId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("DishVariantLocale not found with id: " + id));
    }

    @Transactional
    @Override
    public SuccessResponse update(DishVariantLocaleEntity entity, UpdateDishVariantLocaleRequest request) {
        DishVariantLocaleMapper.update(entity, request);
        dishVariantLocaleRepository.save(entity);
        log.info("DishVariantLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(DishVariantLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        dishVariantLocaleRepository.save(entity);
        log.info("DishVariantLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
