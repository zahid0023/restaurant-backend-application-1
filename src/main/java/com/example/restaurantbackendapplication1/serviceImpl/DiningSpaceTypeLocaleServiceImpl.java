package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.CreateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.UpdateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.mapper.DiningSpaceTypeLocaleMapper;
import com.example.restaurantbackendapplication1.repository.DiningSpaceTypeLocaleRepository;
import com.example.restaurantbackendapplication1.service.DiningSpaceTypeLocaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DiningSpaceTypeLocaleServiceImpl implements DiningSpaceTypeLocaleService {

    private final DiningSpaceTypeLocaleRepository diningSpaceTypeLocaleRepository;

    public DiningSpaceTypeLocaleServiceImpl(DiningSpaceTypeLocaleRepository diningSpaceTypeLocaleRepository) {
        this.diningSpaceTypeLocaleRepository = diningSpaceTypeLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(DiningSpaceTypeEntity diningSpaceTypeEntity, LocaleEntity localeEntity,
                                  CreateDiningSpaceTypeLocaleRequest request) {
        DiningSpaceTypeLocaleEntity entity = DiningSpaceTypeLocaleMapper.create(request, diningSpaceTypeEntity, localeEntity);
        diningSpaceTypeLocaleRepository.save(entity);
        log.info("DiningSpaceTypeLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DiningSpaceTypeLocaleEntity getEntityById(Long diningSpaceTypeId, Long id) {
        return diningSpaceTypeLocaleRepository
                .findByDiningSpaceTypeEntity_IdAndIdAndIsActiveAndIsDeleted(diningSpaceTypeId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("DiningSpaceTypeLocale not found with id: " + id));
    }

    @Transactional
    @Override
    public SuccessResponse update(DiningSpaceTypeLocaleEntity entity, UpdateDiningSpaceTypeLocaleRequest request) {
        DiningSpaceTypeLocaleMapper.update(entity, request);
        diningSpaceTypeLocaleRepository.save(entity);
        log.info("DiningSpaceTypeLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(DiningSpaceTypeLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        diningSpaceTypeLocaleRepository.save(entity);
        log.info("DiningSpaceTypeLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
