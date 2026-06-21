package com.example.restaurantbackendapplication1.restaurant.repository;

import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantOperatingHourEntity;
import com.example.restaurantbackendapplication1.restaurant.model.projection.RestaurantOperatingHourSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface RestaurantOperatingHourRepository extends JpaRepository<@NonNull RestaurantOperatingHourEntity, @NonNull Long> {
    Optional<RestaurantOperatingHourEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);
    List<RestaurantOperatingHourSummary> findAllByIsActiveAndIsDeletedOrderBySequenceNoAsc(Boolean isActive, Boolean isDeleted);
    boolean existsByIsDeleted(Boolean isDeleted);
    boolean existsByDayOfWeekAndSequenceNoAndIsDeleted(DayOfWeek dayOfWeek, Short sequenceNo, Boolean isDeleted);

    @Modifying
    @Query("UPDATE RestaurantOperatingHourEntity e SET e.isDeleted = true, e.isActive = false, e.deletedAt = CURRENT_TIMESTAMP WHERE e.isDeleted = false")
    void softDeleteAll();
}
