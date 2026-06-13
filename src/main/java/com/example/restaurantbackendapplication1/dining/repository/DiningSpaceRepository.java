package com.example.restaurantbackendapplication1.dining.repository;

import com.example.restaurantbackendapplication1.dining.model.entity.DiningSpaceEntity;
import com.example.restaurantbackendapplication1.dining.model.projection.DiningSpaceSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiningSpaceRepository extends JpaRepository<@NonNull DiningSpaceEntity, @NonNull Long> {
    Optional<DiningSpaceEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);

    Page<DiningSpaceSummary> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted, Pageable pageable);
}
