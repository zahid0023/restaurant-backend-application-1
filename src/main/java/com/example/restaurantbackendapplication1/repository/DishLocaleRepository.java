package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.DishesLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.DishLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishLocaleRepository extends JpaRepository<@NonNull DishesLocaleEntity, @NonNull Long> {

    Optional<DishesLocaleEntity> findByDishEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long dishId, Long id, Boolean isActive, Boolean isDeleted);

    Page<@NonNull DishLocaleSummary> findAllByDishEntity_IdAndIsActiveAndIsDeleted(
            Long dishId, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
