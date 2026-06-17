package com.example.restaurantbackendapplication1.restaurant.repository;

import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoLocaleEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantBasicInfoLocaleRepository extends JpaRepository<@NonNull RestaurantBasicInfoLocaleEntity, @NonNull Long> {
    Optional<RestaurantBasicInfoLocaleEntity> findByRestaurantBasicInfoEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long restaurantBasicInfoId, Long id, Boolean isActive, Boolean isDeleted);
}
