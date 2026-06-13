package com.example.restaurantbackendapplication1.dish.repository;

import com.example.restaurantbackendapplication1.dish.model.entity.DishLocaleEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishLocaleRepository extends JpaRepository<@NonNull DishLocaleEntity, @NonNull Long> {

    Optional<DishLocaleEntity> findByDishEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long dishId, Long id, Boolean isActive, Boolean isDeleted);
}
