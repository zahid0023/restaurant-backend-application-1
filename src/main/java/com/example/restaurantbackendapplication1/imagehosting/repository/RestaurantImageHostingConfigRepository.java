package com.example.restaurantbackendapplication1.imagehosting.repository;

import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import com.example.restaurantbackendapplication1.imagehosting.model.projection.RestaurantImageHostingConfigSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantImageHostingConfigRepository
        extends JpaRepository<@NonNull RestaurantImageHostingConfigEntity, @NonNull Long> {

    Optional<RestaurantImageHostingConfigEntity> findByIdAndIsActiveAndIsDeleted(
            Long id, Boolean isActive, Boolean isDeleted);

    Page<RestaurantImageHostingConfigSummary> findAllByIsActiveAndIsDeleted(
            Boolean isActive, Boolean isDeleted, Pageable pageable);
}
