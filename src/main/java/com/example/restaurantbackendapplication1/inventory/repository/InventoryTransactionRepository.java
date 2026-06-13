package com.example.restaurantbackendapplication1.inventory.repository;

import com.example.restaurantbackendapplication1.inventory.model.entity.InventoryTransactionEntity;
import com.example.restaurantbackendapplication1.inventory.model.projection.InventoryTransactionSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryTransactionRepository extends JpaRepository<@NonNull InventoryTransactionEntity, @NonNull Long> {
    Optional<InventoryTransactionEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);

    Page<InventoryTransactionSummary> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted, Pageable pageable);
}
