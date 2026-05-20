package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.DishesLocaleEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishLocaleRepository extends JpaRepository<@NonNull DishesLocaleEntity, @NonNull Long> {

    Optional<DishesLocaleEntity> findByDishEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long dishId, Long id, Boolean isActive, Boolean isDeleted);
}
