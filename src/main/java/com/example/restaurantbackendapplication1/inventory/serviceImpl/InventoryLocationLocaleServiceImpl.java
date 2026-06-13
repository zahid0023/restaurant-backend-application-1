package com.example.restaurantbackendapplication1.inventory.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.inventory.dto.request.inventorylocationlocale.CreateInventoryLocationLocaleRequest;
import com.example.restaurantbackendapplication1.inventory.dto.request.inventorylocationlocale.UpdateInventoryLocationLocaleRequest;
import com.example.restaurantbackendapplication1.inventory.dto.response.InventoryLocationLocaleResponse;
import com.example.restaurantbackendapplication1.inventory.model.dto.InventoryLocationLocaleDto;
import com.example.restaurantbackendapplication1.inventory.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.inventory.model.entity.InventoryLocationLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.inventory.model.enums.InventoryLocationLocaleSortField;
import com.example.restaurantbackendapplication1.inventory.model.mapper.InventoryLocationLocaleMapper;
import com.example.restaurantbackendapplication1.inventory.model.projection.InventoryLocationLocaleSummary;
import com.example.restaurantbackendapplication1.inventory.repository.InventoryLocationLocaleRepository;
import com.example.restaurantbackendapplication1.inventory.service.InventoryLocationLocaleService;
import com.example.restaurantbackendapplication1.commons.utils.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Slf4j
public class InventoryLocationLocaleServiceImpl implements InventoryLocationLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = InventoryLocationLocaleSortField.allowedFields();

    private final InventoryLocationLocaleRepository inventoryLocationLocaleRepository;

    public InventoryLocationLocaleServiceImpl(InventoryLocationLocaleRepository inventoryLocationLocaleRepository) {
        this.inventoryLocationLocaleRepository = inventoryLocationLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(InventoryLocationEntity inventoryLocationEntity, LocaleEntity localeEntity,
                                  CreateInventoryLocationLocaleRequest request) {
        InventoryLocationLocaleEntity entity = InventoryLocationLocaleMapper.fromRequest(
                request, inventoryLocationEntity, localeEntity);
        inventoryLocationLocaleRepository.save(entity);
        log.info("InventoryLocationLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public InventoryLocationLocaleEntity getEntityById(Long id, InventoryLocationEntity inventoryLocationEntity) {
        return inventoryLocationLocaleRepository
                .findByIdAndInventoryLocationEntityAndIsActiveAndIsDeleted(id, inventoryLocationEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("InventoryLocationLocale not found with id: " + id));
    }

    @Override
    public InventoryLocationLocaleResponse getById(Long id, InventoryLocationEntity inventoryLocationEntity) {
        InventoryLocationLocaleEntity entity = getEntityById(id, inventoryLocationEntity);
        InventoryLocationLocaleDto dto = InventoryLocationLocaleMapper.toDto(entity);
        return new InventoryLocationLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<InventoryLocationLocaleSummary> getAll(InventoryLocationEntity inventoryLocationEntity,
                                                                    PaginatedRequest request) {
        Page<@NonNull InventoryLocationLocaleSummary> page = inventoryLocationLocaleRepository
                .findAllByInventoryLocationEntityAndIsActiveAndIsDeleted(
                        inventoryLocationEntity, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(InventoryLocationLocaleEntity entity, LocaleEntity localeEntity,
                                  UpdateInventoryLocationLocaleRequest request) {
        InventoryLocationLocaleMapper.update(entity, request, localeEntity);
        inventoryLocationLocaleRepository.save(entity);
        log.info("InventoryLocationLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(InventoryLocationLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        inventoryLocationLocaleRepository.save(entity);
        log.info("InventoryLocationLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
