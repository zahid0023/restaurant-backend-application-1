package com.example.restaurantbackendapplication1.item.repository;

import com.example.restaurantbackendapplication1.item.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemItemCategoryEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemItemCategoryRepository extends JpaRepository<@NonNull ItemItemCategoryEntity, @NonNull Long> {
    Optional<ItemItemCategoryEntity> findByItemCategoryEntityAndItemEntityAndIsActiveAndIsDeleted(
            ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity, Boolean isActive, Boolean isDeleted);

    boolean existsByItemCategoryEntityAndItemEntityAndIsActiveAndIsDeleted(
            ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity, Boolean isActive, Boolean isDeleted);
}
