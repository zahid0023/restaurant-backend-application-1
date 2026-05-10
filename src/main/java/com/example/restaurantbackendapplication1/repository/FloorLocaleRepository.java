package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.model.entity.FloorLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.FloorLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FloorLocaleRepository extends JpaRepository<@NonNull FloorLocaleEntity, @NonNull Long> {
    Optional<FloorLocaleEntity> findByIdAndFloorEntityAndIsActiveAndIsDeleted(
            Long id, FloorEntity floorEntity, Boolean isActive, Boolean isDeleted);

    Page<FloorLocaleSummary> findAllByFloorEntityAndIsActiveAndIsDeleted(
            FloorEntity floorEntity, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
