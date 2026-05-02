package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.unittypelocale.CreateUnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.unittypelocale.UpdateUnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.UnitTypeLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.UnitTypeLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.UnitTypeLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.UnitTypeLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.UnitTypeLocaleSummary;
import com.example.restaurantbackendapplication1.repository.UnitTypeLocaleRepository;
import com.example.restaurantbackendapplication1.service.UnitTypeLocaleService;
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
public class UnitTypeLocaleServiceImpl implements UnitTypeLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = UnitTypeLocaleSortField.allowedFields();

    private final UnitTypeLocaleRepository unitTypeLocaleRepository;

    public UnitTypeLocaleServiceImpl(UnitTypeLocaleRepository unitTypeLocaleRepository) {
        this.unitTypeLocaleRepository = unitTypeLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(UnitTypeEntity unitType, LocaleEntity locale, CreateUnitTypeLocaleRequest request) {
        UnitTypeLocaleEntity entity = UnitTypeLocaleMapper.fromRequest(request, unitType, locale);
        unitTypeLocaleRepository.save(entity);
        log.info("UnitTypeLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public UnitTypeLocaleResponse getById(Long id, UnitTypeEntity unitType) {
        UnitTypeLocaleEntity entity = getEntityById(id, unitType);
        UnitTypeLocaleDto dto = UnitTypeLocaleMapper.toDto(entity);
        return new UnitTypeLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<UnitTypeLocaleSummary> getAll(UnitTypeEntity unitType, PaginatedRequest request) {
        Page<@NonNull UnitTypeLocaleSummary> page = unitTypeLocaleRepository
                .findAllByUnitTypeEntityAndIsActiveAndIsDeleted(
                        unitType, true, false, request.toPageable(ALLOWED_SORT_FIELDS)
                );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(UnitTypeLocaleEntity entity, LocaleEntity locale, UpdateUnitTypeLocaleRequest request) {
        UnitTypeLocaleMapper.update(entity, request, locale);
        unitTypeLocaleRepository.save(entity);
        log.info("UnitTypeLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(UnitTypeLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        unitTypeLocaleRepository.save(entity);
        log.info("UnitTypeLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public UnitTypeLocaleEntity getEntityById(Long id, UnitTypeEntity unitType) {
        return unitTypeLocaleRepository
                .findByIdAndUnitTypeEntityAndIsActiveAndIsDeleted(id, unitType, true, false)
                .orElseThrow(() -> new EntityNotFoundException("UnitTypeLocale not found with id: " + id));
    }
}
