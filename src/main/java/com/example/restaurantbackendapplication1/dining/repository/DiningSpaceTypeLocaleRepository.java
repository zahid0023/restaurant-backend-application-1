package com.example.restaurantbackendapplication1.dining.repository;

import com.example.restaurantbackendapplication1.dining.model.entity.DiningSpaceTypeLocaleEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiningSpaceTypeLocaleRepository extends JpaRepository<@NonNull DiningSpaceTypeLocaleEntity, @NonNull Long> {
    Optional<DiningSpaceTypeLocaleEntity> findByDiningSpaceTypeEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long diningSpaceTypeId, Long id, Boolean isActive, Boolean isDeleted);
}
