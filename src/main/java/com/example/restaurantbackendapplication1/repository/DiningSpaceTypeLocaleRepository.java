package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.DiningSpaceTypeLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiningSpaceTypeLocaleRepository extends JpaRepository<@NonNull DiningSpaceTypeLocaleEntity, @NonNull Long> {
    Optional<DiningSpaceTypeLocaleEntity> findByIdAndDiningSpaceTypeEntityAndIsActiveAndIsDeleted(
            Long id, DiningSpaceTypeEntity diningSpaceType, Boolean isActive, Boolean isDeleted);

    Page<@NonNull DiningSpaceTypeLocaleSummary> findAllByDiningSpaceTypeEntityAndIsActiveAndIsDeleted(
            DiningSpaceTypeEntity diningSpaceType, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
