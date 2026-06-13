package com.example.restaurantbackendapplication1.unit.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.unit.dto.request.unittype.CreateUnitTypeRequest;
import com.example.restaurantbackendapplication1.unit.dto.request.unittype.UpdateUnitTypeRequest;
import com.example.restaurantbackendapplication1.unit.dto.response.UnitTypeResponse;
import com.example.restaurantbackendapplication1.unit.model.dto.UnitTypeDto;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.unit.model.enums.UnitTypeSortField;
import com.example.restaurantbackendapplication1.unit.model.mapper.UnitTypeMapper;
import com.example.restaurantbackendapplication1.unit.model.projection.UnitTypeSummary;
import com.example.restaurantbackendapplication1.unit.repository.UnitTypeRepository;
import com.example.restaurantbackendapplication1.unit.service.UnitTypeService;
import com.example.restaurantbackendapplication1.commons.utils.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class UnitTypeServiceImpl implements UnitTypeService {

    private static final Set<String> ALLOWED_SORT_FIELDS = UnitTypeSortField.allowedFields();

    private final UnitTypeRepository unitTypeRepository;

    public UnitTypeServiceImpl(UnitTypeRepository unitTypeRepository) {
        this.unitTypeRepository = unitTypeRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateUnitTypeRequest request, Map<Long, LocaleEntity> localeEntityMap) {
        UnitTypeEntity entity = UnitTypeMapper.create(request, localeEntityMap);
        unitTypeRepository.save(entity);
        log.info("UnitType created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public UnitTypeResponse getById(Long id) {
        UnitTypeEntity entity = getEntityById(id);
        UnitTypeDto dto = UnitTypeMapper.toDto(entity);
        return new UnitTypeResponse(dto);
    }

    @Override
    public PaginatedResponse<UnitTypeSummary> getAll(PaginatedRequest request) {
        Page<@NonNull UnitTypeSummary> page = unitTypeRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS)
        );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(UnitTypeEntity entity, UpdateUnitTypeRequest request) {
        UnitTypeMapper.update(entity, request);
        unitTypeRepository.save(entity);
        log.info("UnitType updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long id) {
        UnitTypeEntity entity = getEntityById(id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        unitTypeRepository.save(entity);
        log.info("UnitType soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public UnitTypeEntity getEntityById(Long id) {
        return unitTypeRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("UnitType not found with id: " + id));
    }
}
