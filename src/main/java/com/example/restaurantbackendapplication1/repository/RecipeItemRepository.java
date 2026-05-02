package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.RecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.RecipeItemEntity;
import com.example.restaurantbackendapplication1.model.projection.RecipeItemSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeItemRepository extends JpaRepository<@NonNull RecipeItemEntity, @NonNull Long> {
    Optional<RecipeItemEntity> findByIdAndRecipeEntityAndIsActiveAndIsDeleted(
            Long id, RecipeEntity recipeEntity, Boolean isActive, Boolean isDeleted);

    Page<RecipeItemSummary> findAllByRecipeEntityAndIsActiveAndIsDeleted(
            RecipeEntity recipeEntity, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
