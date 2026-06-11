package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.projection.DishSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishRepository extends JpaRepository<@NonNull DishEntity, @NonNull Long> {

    Optional<DishEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);

    Page<@NonNull DishSummary> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted, Pageable pageable);
}
