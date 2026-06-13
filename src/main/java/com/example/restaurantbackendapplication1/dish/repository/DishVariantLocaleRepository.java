package com.example.restaurantbackendapplication1.dish.repository;

import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantLocaleEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishVariantLocaleRepository extends JpaRepository<@NonNull DishVariantLocaleEntity, @NonNull Long> {

    Optional<DishVariantLocaleEntity> findByDishVariantEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long dishVariantId, Long id, Boolean isActive, Boolean isDeleted);
}
