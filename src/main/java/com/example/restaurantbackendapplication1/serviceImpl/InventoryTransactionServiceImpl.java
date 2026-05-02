package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.inventorytransaction.CreateInventoryTransactionRequest;
import com.example.restaurantbackendapplication1.dto.request.inventorytransaction.UpdateInventoryTransactionRequest;
import com.example.restaurantbackendapplication1.dto.response.InventoryTransactionResponse;
import com.example.restaurantbackendapplication1.model.dto.InventoryTransactionDto;
import com.example.restaurantbackendapplication1.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.model.entity.InventoryTransactionEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.enums.InventoryTransactionSortField;
import com.example.restaurantbackendapplication1.model.enums.TransactionType;
import com.example.restaurantbackendapplication1.model.mapper.InventoryTransactionMapper;
import com.example.restaurantbackendapplication1.model.projection.InventoryTransactionSummary;
import com.example.restaurantbackendapplication1.repository.InventoryTransactionRepository;
import com.example.restaurantbackendapplication1.service.InventoryTransactionService;
import com.example.restaurantbackendapplication1.utils.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Service
@Slf4j
public class InventoryTransactionServiceImpl implements InventoryTransactionService {

    private static final Set<String> ALLOWED_SORT_FIELDS = InventoryTransactionSortField.allowedFields();

    private final InventoryTransactionRepository inventoryTransactionRepository;

    public InventoryTransactionServiceImpl(InventoryTransactionRepository inventoryTransactionRepository) {
        this.inventoryTransactionRepository = inventoryTransactionRepository;
    }

    private void validateQuantitySign(TransactionType type, BigDecimal quantity) {
        boolean isIn = type == TransactionType.PURCHASE
                || type == TransactionType.TRANSFER_IN
                || type == TransactionType.RETURN;
        boolean isOut = type == TransactionType.SALE
                || type == TransactionType.CONSUME
                || type == TransactionType.TRANSFER_OUT
                || type == TransactionType.RETURN_TO_SUPPLIER;

        if (isIn && quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    type + " requires a positive quantity (stock IN)");
        }
        if (isOut && quantity.compareTo(BigDecimal.ZERO) >= 0) {
            throw new IllegalArgumentException(
                    type + " requires a negative quantity (stock OUT)");
        }
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateInventoryTransactionRequest request,
                                  ItemEntity itemEntity,
                                  InventoryLocationEntity inventoryLocationEntity) {
        validateQuantitySign(request.getTransactionType(), request.getQuantity());
        InventoryTransactionEntity entity = InventoryTransactionMapper.fromRequest(
                request, itemEntity, inventoryLocationEntity);
        inventoryTransactionRepository.save(entity);
        log.info("InventoryTransaction created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public InventoryTransactionEntity getEntityById(Long id) {
        return inventoryTransactionRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("InventoryTransaction not found with id: " + id));
    }

    @Override
    public InventoryTransactionResponse getById(Long id) {
        InventoryTransactionEntity entity = getEntityById(id);
        InventoryTransactionDto dto = InventoryTransactionMapper.toDto(entity);
        return new InventoryTransactionResponse(dto);
    }

    @Override
    public PaginatedResponse<InventoryTransactionSummary> getAll(PaginatedRequest request) {
        Page<@NonNull InventoryTransactionSummary> page = inventoryTransactionRepository
                .findAllByIsActiveAndIsDeleted(true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(InventoryTransactionEntity entity,
                                  UpdateInventoryTransactionRequest request,
                                  ItemEntity itemEntity,
                                  InventoryLocationEntity inventoryLocationEntity) {
        validateQuantitySign(request.getTransactionType(), request.getQuantity());
        InventoryTransactionMapper.update(entity, request, itemEntity, inventoryLocationEntity);
        inventoryTransactionRepository.save(entity);
        log.info("InventoryTransaction updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long id) {
        InventoryTransactionEntity entity = getEntityById(id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        inventoryTransactionRepository.save(entity);
        log.info("InventoryTransaction soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
