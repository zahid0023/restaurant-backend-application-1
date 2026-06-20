package com.example.restaurantbackendapplication1.dish.repository;

import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantImageEntity;
import com.example.restaurantbackendapplication1.dish.model.projection.DishVariantImageSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishVariantImageRepository extends JpaRepository<@NonNull DishVariantImageEntity, @NonNull Long> {
    Optional<DishVariantImageEntity> findByIdAndDishVariantEntity_IdAndIsActiveAndIsDeleted(Long id, Long dishVariantId, Boolean isActive, Boolean isDeleted);
    Page<DishVariantImageSummary> findAllByDishVariantEntity_IdAndIsActiveAndIsDeleted(Long dishVariantId, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
