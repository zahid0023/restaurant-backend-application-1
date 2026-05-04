package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.DishVariantLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.DishVariantLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishVariantLocaleRepository extends JpaRepository<@NonNull DishVariantLocaleEntity, @NonNull Long> {

    Optional<DishVariantLocaleEntity> findByDishVariantEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long dishVariantId, Long id, Boolean isActive, Boolean isDeleted);

    Page<@NonNull DishVariantLocaleSummary> findAllByDishVariantEntity_IdAndIsActiveAndIsDeleted(
            Long dishVariantId, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
