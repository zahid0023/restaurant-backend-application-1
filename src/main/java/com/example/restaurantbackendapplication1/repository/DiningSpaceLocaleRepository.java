package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.DiningSpaceEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.DiningSpaceLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiningSpaceLocaleRepository extends JpaRepository<@NonNull DiningSpaceLocaleEntity, @NonNull Long> {
    Optional<DiningSpaceLocaleEntity> findByIdAndDiningSpaceEntityAndIsActiveAndIsDeleted(
            Long id, DiningSpaceEntity diningSpace, Boolean isActive, Boolean isDeleted);

    Page<@NonNull DiningSpaceLocaleSummary> findAllByDiningSpaceEntityAndIsActiveAndIsDeleted(
            DiningSpaceEntity diningSpace, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
