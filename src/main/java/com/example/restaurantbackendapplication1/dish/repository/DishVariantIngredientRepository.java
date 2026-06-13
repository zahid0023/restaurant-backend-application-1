package com.example.restaurantbackendapplication1.dish.repository;

import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantIngredientEntity;
import com.example.restaurantbackendapplication1.dish.model.projection.DishVariantIngredientSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishVariantIngredientRepository extends JpaRepository<@NonNull DishVariantIngredientEntity, @NonNull Long> {

    Optional<DishVariantIngredientEntity> findByDishVariantEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long dishVariantId, Long id, Boolean isActive, Boolean isDeleted);

    Page<@NonNull DishVariantIngredientSummary> findAllByDishVariantEntity_IdAndIsActiveAndIsDeleted(
            Long dishVariantId, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
