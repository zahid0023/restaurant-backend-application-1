package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.UnitTypeLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitTypeLocaleRepository extends JpaRepository<@NonNull UnitTypeLocaleEntity, @NonNull Long> {
    Optional<UnitTypeLocaleEntity> findByIdAndUnitTypeEntityAndIsActiveAndIsDeleted(
            Long id, UnitTypeEntity unitType, Boolean isActive, Boolean isDeleted);

    Page<@NonNull UnitTypeLocaleSummary> findAllByUnitTypeEntityAndIsActiveAndIsDeleted(
            UnitTypeEntity unitType, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
