package com.example.restaurantbackendapplication1.inventory.repository;

import com.example.restaurantbackendapplication1.inventory.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.inventory.model.entity.InventoryLocationLocaleEntity;
import com.example.restaurantbackendapplication1.inventory.model.projection.InventoryLocationLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryLocationLocaleRepository extends JpaRepository<@NonNull InventoryLocationLocaleEntity, @NonNull Long> {
    Optional<InventoryLocationLocaleEntity> findByIdAndInventoryLocationEntityAndIsActiveAndIsDeleted(
            Long id, InventoryLocationEntity inventoryLocationEntity, Boolean isActive, Boolean isDeleted);

    Page<InventoryLocationLocaleSummary> findAllByInventoryLocationEntityAndIsActiveAndIsDeleted(
            InventoryLocationEntity inventoryLocationEntity, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
