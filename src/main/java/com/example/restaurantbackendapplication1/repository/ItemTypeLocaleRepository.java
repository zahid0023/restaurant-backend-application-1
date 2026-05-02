package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.ItemTypeLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemTypeLocaleRepository extends JpaRepository<@NonNull ItemTypeLocaleEntity, @NonNull Long> {
    Optional<ItemTypeLocaleEntity> findByIdAndItemTypeEntityAndIsActiveAndIsDeleted(
            Long id, ItemTypeEntity itemType, Boolean isActive, Boolean isDeleted);

    Page<@NonNull ItemTypeLocaleSummary> findAllByItemTypeEntityAndIsActiveAndIsDeleted(
            ItemTypeEntity itemType, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
