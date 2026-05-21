package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.FloorLocaleEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FloorLocaleRepository extends JpaRepository<@NonNull FloorLocaleEntity, @NonNull Long> {
    Optional<FloorLocaleEntity> findByFloorEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long floorId, Long id, Boolean isActive, Boolean isDeleted);
}
