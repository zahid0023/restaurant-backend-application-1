package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.projection.DishRecipeSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishRecipeRepository extends JpaRepository<@NonNull DishRecipeEntity, @NonNull Long> {

    Optional<DishRecipeEntity> findByDishVariantEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long dishVariantId, Long id, Boolean isActive, Boolean isDeleted);

    Page<@NonNull DishRecipeSummary> findAllByDishVariantEntity_IdAndIsActiveAndIsDeleted(
            Long dishVariantId, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
