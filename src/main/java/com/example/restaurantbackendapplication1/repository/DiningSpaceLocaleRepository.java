package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.DiningSpaceLocaleEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiningSpaceLocaleRepository extends JpaRepository<@NonNull DiningSpaceLocaleEntity, @NonNull Long> {
    Optional<DiningSpaceLocaleEntity> findByDiningSpaceEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long diningSpaceId, Long id, Boolean isActive, Boolean isDeleted);
}
