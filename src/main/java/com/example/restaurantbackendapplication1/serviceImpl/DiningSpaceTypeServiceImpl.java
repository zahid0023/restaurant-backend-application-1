package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.diningspacetype.CreateDiningSpaceTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetype.UpdateDiningSpaceTypeRequest;
import com.example.restaurantbackendapplication1.dto.response.DiningSpaceTypeResponse;
import com.example.restaurantbackendapplication1.model.dto.DiningSpaceTypeDto;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.DiningSpaceTypeSortField;
import com.example.restaurantbackendapplication1.model.mapper.DiningSpaceTypeMapper;
import com.example.restaurantbackendapplication1.model.projection.DiningSpaceTypeSummary;
import com.example.restaurantbackendapplication1.repository.DiningSpaceTypeRepository;
import com.example.restaurantbackendapplication1.service.DiningSpaceTypeService;
import com.example.restaurantbackendapplication1.utils.Pagination;
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
public class DiningSpaceTypeServiceImpl implements DiningSpaceTypeService {

    private static final Set<String> ALLOWED_SORT_FIELDS = DiningSpaceTypeSortField.allowedFields();

    private final DiningSpaceTypeRepository diningSpaceTypeRepository;

    public DiningSpaceTypeServiceImpl(DiningSpaceTypeRepository diningSpaceTypeRepository) {
        this.diningSpaceTypeRepository = diningSpaceTypeRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateDiningSpaceTypeRequest request, Map<Long, LocaleEntity> localeEntityMap) {
        DiningSpaceTypeEntity entity = DiningSpaceTypeMapper.create(request, localeEntityMap);
        diningSpaceTypeRepository.save(entity);
        log.info("DiningSpaceType created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DiningSpaceTypeResponse getById(Long id) {
        DiningSpaceTypeEntity entity = getEntityById(id);
        DiningSpaceTypeDto dto = DiningSpaceTypeMapper.toDto(entity);
        return new DiningSpaceTypeResponse(dto);
    }

    @Override
    public PaginatedResponse<DiningSpaceTypeSummary> getAll(PaginatedRequest request) {
        Page<@NonNull DiningSpaceTypeSummary> page = diningSpaceTypeRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS)
        );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(DiningSpaceTypeEntity entity, UpdateDiningSpaceTypeRequest request) {
        DiningSpaceTypeMapper.update(entity, request);
        diningSpaceTypeRepository.save(entity);
        log.info("DiningSpaceType updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long id) {
        DiningSpaceTypeEntity entity = getEntityById(id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        diningSpaceTypeRepository.save(entity);
        log.info("DiningSpaceType soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DiningSpaceTypeEntity getEntityById(Long id) {
        return diningSpaceTypeRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("DiningSpaceType not found with id: " + id));
    }
}
