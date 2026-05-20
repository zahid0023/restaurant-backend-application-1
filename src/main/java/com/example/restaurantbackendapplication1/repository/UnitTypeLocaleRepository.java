package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.UnitTypeLocaleEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitTypeLocaleRepository extends JpaRepository<@NonNull UnitTypeLocaleEntity, @NonNull Long> {
    Optional<UnitTypeLocaleEntity> findByUnitTypeEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long unitTypeId, Long id, Boolean isActive, Boolean isDeleted);
}
