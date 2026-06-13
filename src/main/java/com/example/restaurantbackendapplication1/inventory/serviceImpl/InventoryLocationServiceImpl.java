package com.example.restaurantbackendapplication1.inventory.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.inventory.dto.request.inventorylocation.CreateInventoryLocationRequest;
import com.example.restaurantbackendapplication1.inventory.dto.request.inventorylocation.UpdateInventoryLocationRequest;
import com.example.restaurantbackendapplication1.inventory.dto.response.InventoryLocationResponse;
import com.example.restaurantbackendapplication1.inventory.model.dto.InventoryLocationDto;
import com.example.restaurantbackendapplication1.inventory.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.inventory.model.enums.InventoryLocationSortField;
import com.example.restaurantbackendapplication1.inventory.model.mapper.InventoryLocationMapper;
import com.example.restaurantbackendapplication1.inventory.model.projection.InventoryLocationSummary;
import com.example.restaurantbackendapplication1.inventory.repository.InventoryLocationRepository;
import com.example.restaurantbackendapplication1.inventory.service.InventoryLocationService;
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
public class InventoryLocationServiceImpl implements InventoryLocationService {

    private static final Set<String> ALLOWED_SORT_FIELDS = InventoryLocationSortField.allowedFields();

    private final InventoryLocationRepository inventoryLocationRepository;

    public InventoryLocationServiceImpl(InventoryLocationRepository inventoryLocationRepository) {
        this.inventoryLocationRepository = inventoryLocationRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateInventoryLocationRequest request, Map<Long, LocaleEntity> localeEntityMap) {
        InventoryLocationEntity entity = InventoryLocationMapper.fromRequest(request, localeEntityMap);
        inventoryLocationRepository.save(entity);
        log.info("InventoryLocation created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public InventoryLocationEntity getEntityById(Long id) {
        return inventoryLocationRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("InventoryLocation not found with id: " + id));
    }

    @Override
    public InventoryLocationResponse getById(Long id) {
        InventoryLocationEntity entity = getEntityById(id);
        InventoryLocationDto dto = InventoryLocationMapper.toDto(entity);
        return new InventoryLocationResponse(dto);
    }

    @Override
    public PaginatedResponse<InventoryLocationSummary> getAll(PaginatedRequest request) {
        Page<@NonNull InventoryLocationSummary> page = inventoryLocationRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS)
        );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(InventoryLocationEntity entity, UpdateInventoryLocationRequest request) {
        InventoryLocationMapper.update(entity, request);
        inventoryLocationRepository.save(entity);
        log.info("InventoryLocation updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long id) {
        InventoryLocationEntity entity = getEntityById(id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        inventoryLocationRepository.save(entity);
        log.info("InventoryLocation soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
