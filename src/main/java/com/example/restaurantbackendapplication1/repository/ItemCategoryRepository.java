package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.projection.ItemCategorySummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemCategoryRepository extends JpaRepository<@NonNull ItemCategoryEntity, @NonNull Long> {

    Optional<ItemCategoryEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);

    Page<@NonNull ItemCategorySummary> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted, Pageable pageable);

    Page<@NonNull ItemCategorySummary> findAllByItemCategoryEntityIsNullAndIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted, Pageable pageable);

    Page<@NonNull ItemCategorySummary> findAllByItemCategoryEntity_IdAndIsActiveAndIsDeleted(Long itemCategoryId, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
