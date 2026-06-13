package com.example.restaurantbackendapplication1.inventory.repository;

import com.example.restaurantbackendapplication1.inventory.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.inventory.model.projection.InventoryLocationSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryLocationRepository extends JpaRepository<@NonNull InventoryLocationEntity, @NonNull Long> {
    Optional<InventoryLocationEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);

    Page<InventoryLocationSummary> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted, Pageable pageable);
}
