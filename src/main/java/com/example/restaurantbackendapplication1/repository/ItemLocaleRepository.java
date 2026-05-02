package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.ItemLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemLocaleRepository extends JpaRepository<@NonNull ItemLocaleEntity, @NonNull Long> {
    Optional<ItemLocaleEntity> findByIdAndItemEntityAndIsActiveAndIsDeleted(
            Long id, ItemEntity itemEntity, Boolean isActive, Boolean isDeleted);

    Page<@NonNull ItemLocaleSummary> findAllByItemEntityAndIsActiveAndIsDeleted(
            ItemEntity itemEntity, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
