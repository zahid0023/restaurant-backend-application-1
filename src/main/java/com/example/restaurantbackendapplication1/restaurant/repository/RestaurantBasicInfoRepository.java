package com.example.restaurantbackendapplication1.restaurant.repository;

import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoEntity;
import com.example.restaurantbackendapplication1.restaurant.model.projection.RestaurantBasicInfoSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantBasicInfoRepository extends JpaRepository<@NonNull RestaurantBasicInfoEntity, @NonNull Long> {
    Optional<RestaurantBasicInfoEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);

    Page<RestaurantBasicInfoSummary> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted, Pageable pageable);
}
