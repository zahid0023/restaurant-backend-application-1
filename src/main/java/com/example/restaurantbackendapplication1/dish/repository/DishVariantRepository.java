package com.example.restaurantbackendapplication1.dish.repository;

import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.dish.model.projection.DishVariantSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishVariantRepository extends JpaRepository<@NonNull DishVariantEntity, @NonNull Long> {

    Optional<DishVariantEntity> findByDishEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long dishId, Long id, Boolean isActive, Boolean isDeleted);

    Page<@NonNull DishVariantSummary> findAllByDishEntity_IdAndIsActiveAndIsDeleted(
            Long dishId, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
