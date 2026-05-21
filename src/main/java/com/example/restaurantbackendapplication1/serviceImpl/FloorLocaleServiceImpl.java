package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.floor.floorlocale.CreateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.floor.floorlocale.UpdateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.model.entity.FloorLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.mapper.FloorLocaleMapper;
import com.example.restaurantbackendapplication1.repository.FloorLocaleRepository;
import com.example.restaurantbackendapplication1.service.FloorLocaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class FloorLocaleServiceImpl implements FloorLocaleService {

    private final FloorLocaleRepository floorLocaleRepository;

    public FloorLocaleServiceImpl(FloorLocaleRepository floorLocaleRepository) {
        this.floorLocaleRepository = floorLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(FloorEntity floorEntity, LocaleEntity localeEntity,
                                  CreateFloorLocaleRequest request) {
        FloorLocaleEntity entity = FloorLocaleMapper.create(request, floorEntity, localeEntity);
        floorLocaleRepository.save(entity);
        log.info("FloorLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public FloorLocaleEntity getEntityById(Long floorId, Long id) {
        return floorLocaleRepository
                .findByFloorEntity_IdAndIdAndIsActiveAndIsDeleted(floorId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("FloorLocale not found with id: " + id));
    }

    @Transactional
    @Override
    public SuccessResponse update(FloorLocaleEntity entity, UpdateFloorLocaleRequest request) {
        FloorLocaleMapper.update(entity, request);
        floorLocaleRepository.save(entity);
        log.info("FloorLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(FloorLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        floorLocaleRepository.save(entity);
        log.info("FloorLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
