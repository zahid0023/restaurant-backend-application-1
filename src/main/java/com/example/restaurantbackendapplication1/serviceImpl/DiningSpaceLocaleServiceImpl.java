package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.CreateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.UpdateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.mapper.DiningSpaceLocaleMapper;
import com.example.restaurantbackendapplication1.repository.DiningSpaceLocaleRepository;
import com.example.restaurantbackendapplication1.service.DiningSpaceLocaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DiningSpaceLocaleServiceImpl implements DiningSpaceLocaleService {

    private final DiningSpaceLocaleRepository diningSpaceLocaleRepository;

    public DiningSpaceLocaleServiceImpl(DiningSpaceLocaleRepository diningSpaceLocaleRepository) {
        this.diningSpaceLocaleRepository = diningSpaceLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(DiningSpaceEntity diningSpace, LocaleEntity locale,
                                  CreateDiningSpaceLocaleRequest request) {
        DiningSpaceLocaleEntity entity = DiningSpaceLocaleMapper.create(request, diningSpace, locale);
        diningSpaceLocaleRepository.save(entity);
        log.info("DiningSpaceLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DiningSpaceLocaleEntity getEntityById(Long diningSpaceId, Long id) {
        return diningSpaceLocaleRepository
                .findByDiningSpaceEntity_IdAndIdAndIsActiveAndIsDeleted(diningSpaceId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("DiningSpaceLocale not found with id: " + id));
    }

    @Transactional
    @Override
    public SuccessResponse update(DiningSpaceLocaleEntity entity, UpdateDiningSpaceLocaleRequest request) {
        DiningSpaceLocaleMapper.update(entity, request);
        diningSpaceLocaleRepository.save(entity);
        log.info("DiningSpaceLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(DiningSpaceLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        diningSpaceLocaleRepository.save(entity);
        log.info("DiningSpaceLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
