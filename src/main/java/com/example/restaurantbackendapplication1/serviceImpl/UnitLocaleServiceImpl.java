package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.unitlocale.CreateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.unitlocale.UpdateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.UnitLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.UnitLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitLocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.UnitLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.UnitLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.UnitLocaleSummary;
import com.example.restaurantbackendapplication1.repository.UnitLocaleRepository;
import com.example.restaurantbackendapplication1.service.UnitLocaleService;
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
public class UnitLocaleServiceImpl implements UnitLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = UnitLocaleSortField.allowedFields();

    private final UnitLocaleRepository unitLocaleRepository;

    public UnitLocaleServiceImpl(UnitLocaleRepository unitLocaleRepository) {
        this.unitLocaleRepository = unitLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(UnitEntity unitEntity, LocaleEntity locale, CreateUnitLocaleRequest request) {
        UnitLocaleEntity entity = UnitLocaleMapper.fromRequest(request, unitEntity, locale);
        unitLocaleRepository.save(entity);
        log.info("UnitLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public UnitLocaleResponse getById(Long id, UnitEntity unitEntity) {
        UnitLocaleEntity entity = getEntityById(id, unitEntity);
        UnitLocaleDto dto = UnitLocaleMapper.toDto(entity);
        return new UnitLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<UnitLocaleSummary> getAll(UnitEntity unitEntity, PaginatedRequest request) {
        Page<@NonNull UnitLocaleSummary> page = unitLocaleRepository
                .findAllByUnitEntityAndIsActiveAndIsDeleted(
                        unitEntity, true, false, request.toPageable(ALLOWED_SORT_FIELDS)
                );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(UnitLocaleEntity entity, LocaleEntity locale, UpdateUnitLocaleRequest request) {
        UnitLocaleMapper.update(entity, request, locale);
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
