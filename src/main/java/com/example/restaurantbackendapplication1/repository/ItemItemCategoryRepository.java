package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.projection.ItemSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemItemCategoryRepository extends JpaRepository<@NonNull ItemItemCategoryEntity, @NonNull Long> {
    Optional<ItemItemCategoryEntity> findByItemCategoryEntityAndItemEntityAndIsActiveAndIsDeleted(
            ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity, Boolean isActive, Boolean isDeleted);

    Page<@NonNull ItemSummary> findAllByItemCategoryEntityAndIsActiveAndIsDeleted(
            ItemCategoryEntity itemCategoryEntity, Boolean isActive, Boolean isDeleted, Pageable pageable);

    boolean existsByItemCategoryEntityAndItemEntityAndIsActiveAndIsDeleted(
            ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity, Boolean isActive, Boolean isDeleted);
}
