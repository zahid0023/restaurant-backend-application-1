package com.example.restaurantbackendapplication1.item.repository;

import com.example.restaurantbackendapplication1.item.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.item.model.projection.ItemCategoryLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemCategoryLocaleRepository extends JpaRepository<@NonNull ItemCategoryLocaleEntity, @NonNull Long> {
    Optional<ItemCategoryLocaleEntity> findByIdAndItemCategoryEntityAndIsActiveAndIsDeleted(
            Long id, ItemCategoryEntity itemCategoryEntity, Boolean isActive, Boolean isDeleted);

    Page<@NonNull ItemCategoryLocaleSummary> findAllByItemCategoryEntityAndIsActiveAndIsDeleted(
            ItemCategoryEntity itemCategoryEntity, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
