package com.example.restaurantbackendapplication1.unit.repository;

import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitLocaleEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitLocaleRepository extends JpaRepository<@NonNull UnitLocaleEntity, @NonNull Long> {
    Optional<UnitLocaleEntity> findByIdAndUnitEntityAndIsActiveAndIsDeleted(
            Long id, UnitEntity unitEntity, Boolean isActive, Boolean isDeleted);
}
