package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.floor.CreateFloorRequest;
import com.example.restaurantbackendapplication1.dto.request.floor.UpdateFloorRequest;
import com.example.restaurantbackendapplication1.dto.response.FloorResponse;
import com.example.restaurantbackendapplication1.model.dto.FloorDto;
import com.example.restaurantbackendapplication1.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.FloorSortField;
import com.example.restaurantbackendapplication1.model.mapper.FloorMapper;
import com.example.restaurantbackendapplication1.model.projection.FloorSummary;
import com.example.restaurantbackendapplication1.repository.FloorRepository;
import com.example.restaurantbackendapplication1.service.FloorService;
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
public class FloorServiceImpl implements FloorService {

    private static final Set<String> ALLOWED_SORT_FIELDS = FloorSortField.allowedFields();

    private final FloorRepository floorRepository;

    public FloorServiceImpl(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateFloorRequest request, Map<Long, LocaleEntity> localeEntityMap) {
        FloorEntity entity = FloorMapper.fromRequest(request, localeEntityMap);
        floorRepository.save(entity);
        log.info("Floor created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public FloorEntity getEntityById(Long id) {
        return floorRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Floor not found with id: " + id));
    }

    @Override
    public FloorResponse getById(Long id) {
        FloorEntity entity = getEntityById(id);
        FloorDto dto = FloorMapper.toDto(entity);
        return new FloorResponse(dto);
    }

    @Override
    public PaginatedResponse<FloorSummary> getAll(PaginatedRequest request) {
        Page<@NonNull FloorSummary> page = floorRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(FloorEntity entity, UpdateFloorRequest request) {
        FloorMapper.update(entity, request);
        floorRepository.save(entity);
        log.info("Floor updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long id) {
        FloorEntity entity = getEntityById(id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        floorRepository.save(entity);
        log.info("Floor soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
