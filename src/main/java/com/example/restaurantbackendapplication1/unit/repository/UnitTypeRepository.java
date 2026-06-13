package com.example.restaurantbackendapplication1.unit.repository;

import com.example.restaurantbackendapplication1.unit.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.unit.model.projection.UnitTypeSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitTypeRepository extends JpaRepository<@NonNull UnitTypeEntity, @NonNull Long> {
    Optional<UnitTypeEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);

    Page<UnitTypeSummary> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted, Pageable pageable);
}
