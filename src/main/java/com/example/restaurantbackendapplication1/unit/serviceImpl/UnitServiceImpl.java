package com.example.restaurantbackendapplication1.unit.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.unit.dto.request.CreateUnitRequest;
import com.example.restaurantbackendapplication1.unit.dto.request.UpdateUnitRequest;
import com.example.restaurantbackendapplication1.unit.dto.response.UnitResponse;
import com.example.restaurantbackendapplication1.unit.model.dto.UnitDto;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.unit.model.enums.UnitSortField;
import com.example.restaurantbackendapplication1.unit.model.mapper.UnitMapper;
import com.example.restaurantbackendapplication1.unit.model.projection.UnitSummary;
import com.example.restaurantbackendapplication1.unit.repository.UnitRepository;
import com.example.restaurantbackendapplication1.unit.service.UnitService;
import com.example.restaurantbackendapplication1.commons.utils.EntityValidator;
import com.example.restaurantbackendapplication1.commons.utils.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class UnitServiceImpl implements UnitService {

    private static final Set<String> ALLOWED_SORT_FIELDS = UnitSortField.allowedFields();

    private final UnitRepository unitRepository;

    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateUnitRequest request,
                                  UnitTypeEntity unitTypeEntity,
                                  Map<Long, LocaleEntity> localeEntityMap) {
        UnitEntity entity = UnitMapper.create(request, unitTypeEntity, localeEntityMap);
        unitRepository.save(entity);
        log.info("Unit created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public UnitResponse getById(Long unitTypeId, Long id) {
        UnitEntity entity = getEntityById(unitTypeId, id);
        UnitDto dto = UnitMapper.toDto(entity);
        return new UnitResponse(dto);
    }

    @Override
    public PaginatedResponse<UnitSummary> getAll(Long unitTypeId, PaginatedRequest request) {
        Page<@NonNull UnitSummary> page = unitRepository.findAllByUnitTypeEntity_IdAndIsActiveAndIsDeleted(
                unitTypeId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(UnitEntity entity, UpdateUnitRequest request) {
        UnitMapper.update(entity, request);
        unitRepository.save(entity);
        log.info("Unit updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(UnitEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        unitRepository.save(entity);
        log.info("Unit soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public UnitEntity getEntityById(Long id) {
        return unitRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found with id: " + id));
    }

    @Override
    public List<UnitEntity> getAll(Set<Long> ids) {
        List<UnitEntity> entities = unitRepository.findAllByIdInAndIsActiveAndIsDeleted(ids, true, false);
        EntityValidator.validateAllFound(ids, entities, UnitEntity::getId, "Unit");
        return entities;
    }

    @Override
    public UnitEntity getEntityById(Long unitTypeId, Long id) {
        return unitRepository.findByUnitTypeEntity_IdAndIdAndIsActiveAndIsDeleted(unitTypeId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found with id: " + id));
    }
}
