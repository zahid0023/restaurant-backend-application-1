package com.example.restaurantbackendapplication1.restaurant.repository;

import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantImageEntity;
import com.example.restaurantbackendapplication1.restaurant.model.projection.RestaurantImageSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantImageRepository extends JpaRepository<@NonNull RestaurantImageEntity, @NonNull Long> {
    Optional<RestaurantImageEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);
    Page<RestaurantImageSummary> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted, Pageable pageable);
}
