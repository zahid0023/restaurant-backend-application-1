package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.projection.ItemSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemItemCategoryRepository extends JpaRepository<@NonNull ItemItemCategoryEntity, @NonNull Long> {
    Optional<ItemItemCategoryEntity> findByItemCategoryEntityAndItemEntityAndIsActiveAndIsDeleted(
            ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity, Boolean isActive, Boolean isDeleted);

    @Query(value = "SELECT i FROM ItemItemCategoryEntity iic JOIN iic.itemEntity i " +
                   "WHERE iic.itemCategoryEntity.id = :itemCategoryId AND iic.isActive = :isActive AND iic.isDeleted = :isDeleted",
           countQuery = "SELECT COUNT(iic) FROM ItemItemCategoryEntity iic " +
                        "WHERE iic.itemCategoryEntity.id = :itemCategoryId AND iic.isActive = :isActive AND iic.isDeleted = :isDeleted")
    Page<@NonNull ItemSummary> findAllItemsByItemCategoryId(
            @Param("itemCategoryId") Long itemCategoryId,
            @Param("isActive") Boolean isActive,
            @Param("isDeleted") Boolean isDeleted,
            Pageable pageable);

    boolean existsByItemCategoryEntityAndItemEntityAndIsActiveAndIsDeleted(
            ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity, Boolean isActive, Boolean isDeleted);
}
