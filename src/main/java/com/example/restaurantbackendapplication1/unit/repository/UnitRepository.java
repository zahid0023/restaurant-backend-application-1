package com.example.restaurantbackendapplication1.unit.repository;

import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.unit.model.projection.UnitSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UnitRepository extends JpaRepository<@NonNull UnitEntity, @NonNull Long> {
    Optional<UnitEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);

    List<UnitEntity> findAllByIdInAndIsActiveAndIsDeleted(Set<Long> ids, Boolean isActive, Boolean isDeleted);

    Optional<UnitEntity> findByUnitTypeEntity_IdAndIdAndIsActiveAndIsDeleted(Long unitTypeId, Long id, Boolean isActive, Boolean isDeleted);

    Page<@NonNull UnitSummary> findAllByUnitTypeEntity_IdAndIsActiveAndIsDeleted(Long unitTypeId, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
