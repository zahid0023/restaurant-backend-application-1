package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.DishRecipeIngredientEntity;
import com.example.restaurantbackendapplication1.model.projection.DishRecipeIngredientSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishRecipeIngredientRepository extends JpaRepository<@NonNull DishRecipeIngredientEntity, @NonNull Long> {

    Optional<DishRecipeIngredientEntity> findByDishRecipeEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long dishRecipeId, Long id, Boolean isActive, Boolean isDeleted);

    Page<@NonNull DishRecipeIngredientSummary> findAllByDishRecipeEntity_IdAndIsActiveAndIsDeleted(
            Long dishRecipeId, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
