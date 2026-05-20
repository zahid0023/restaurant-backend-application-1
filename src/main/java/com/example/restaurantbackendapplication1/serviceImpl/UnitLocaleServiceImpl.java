package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.unitlocale.CreateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.unitlocale.UpdateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitLocaleEntity;
import com.example.restaurantbackendapplication1.model.mapper.UnitLocaleMapper;
import com.example.restaurantbackendapplication1.repository.UnitLocaleRepository;
import com.example.restaurantbackendapplication1.service.UnitLocaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UnitLocaleServiceImpl implements UnitLocaleService {

    private final UnitLocaleRepository unitLocaleRepository;

    public UnitLocaleServiceImpl(UnitLocaleRepository unitLocaleRepository) {
        this.unitLocaleRepository = unitLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(UnitEntity unitEntity, LocaleEntity locale, CreateUnitLocaleRequest request) {
        UnitLocaleEntity entity = UnitLocaleMapper.create(request, unitEntity, locale);
        unitLocaleRepository.save(entity);
        log.info("UnitLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse update(UnitLocaleEntity entity, UpdateUnitLocaleRequest request) {
        UnitLocaleMapper.update(entity, request);
        unitLocaleRepository.save(entity);
        log.info("UnitLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(UnitLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        unitLocaleRepository.save(entity);
        log.info("UnitLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public UnitLocaleEntity getEntityById(Long id, UnitEntity unitEntity) {
        return unitLocaleRepository
                .findByIdAndUnitEntityAndIsActiveAndIsDeleted(id, unitEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("UnitLocale not found with id: " + id));
    }
}
