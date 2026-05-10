package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.floorlocale.CreateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.floorlocale.UpdateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.FloorLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.FloorLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.model.entity.FloorLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.FloorLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.FloorLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.FloorLocaleSummary;
import com.example.restaurantbackendapplication1.repository.FloorLocaleRepository;
import com.example.restaurantbackendapplication1.service.FloorLocaleService;
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
public class FloorLocaleServiceImpl implements FloorLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = FloorLocaleSortField.allowedFields();

    private final FloorLocaleRepository floorLocaleRepository;

    public FloorLocaleServiceImpl(FloorLocaleRepository floorLocaleRepository) {
        this.floorLocaleRepository = floorLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(FloorEntity floorEntity, LocaleEntity localeEntity,
                                  CreateFloorLocaleRequest request) {
        FloorLocaleEntity entity = FloorLocaleMapper.fromRequest(request, floorEntity, localeEntity);
        floorLocaleRepository.save(entity);
        log.info("FloorLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public FloorLocaleEntity getEntityById(Long id, FloorEntity floorEntity) {
        return floorLocaleRepository
                .findByIdAndFloorEntityAndIsActiveAndIsDeleted(id, floorEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("FloorLocale not found with id: " + id));
    }

    @Override
    public FloorLocaleResponse getById(Long id, FloorEntity floorEntity) {
        FloorLocaleEntity entity = getEntityById(id, floorEntity);
        FloorLocaleDto dto = FloorLocaleMapper.toDto(entity);
        return new FloorLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<FloorLocaleSummary> getAll(FloorEntity floorEntity, PaginatedRequest request) {
        Page<@NonNull FloorLocaleSummary> page = floorLocaleRepository
                .findAllByFloorEntityAndIsActiveAndIsDeleted(
                        floorEntity, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(FloorLocaleEntity entity, LocaleEntity localeEntity,
                                  UpdateFloorLocaleRequest request) {
        FloorLocaleMapper.update(entity, request, localeEntity);
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
