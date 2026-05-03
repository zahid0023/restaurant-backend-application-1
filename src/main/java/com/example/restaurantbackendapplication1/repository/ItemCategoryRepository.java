package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.projection.ItemCategorySummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemCategoryRepository extends JpaRepository<@NonNull ItemCategoryEntity, @NonNull Long> {

    Optional<ItemCategoryEntity> findByItemTypeEntity_IdAndIdAndIsActiveAndIsDeleted(Long itemTypeId, Long id, Boolean isActive, Boolean isDeleted);

    Page<@NonNull ItemCategorySummary> findAllByItemTypeEntityIdAndIsActiveAndIsDeleted(Long itemTypeId, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
