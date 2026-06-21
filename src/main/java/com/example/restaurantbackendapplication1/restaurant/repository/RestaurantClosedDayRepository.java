package com.example.restaurantbackendapplication1.restaurant.repository;

import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantClosedDayEntity;
import com.example.restaurantbackendapplication1.restaurant.model.projection.RestaurantClosedDaySummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.util.List;

public interface RestaurantClosedDayRepository extends JpaRepository<@NonNull RestaurantClosedDayEntity, @NonNull Long> {
    List<RestaurantClosedDaySummary> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted);
    boolean existsByIsDeleted(Boolean isDeleted);
    boolean existsByDayOfWeekAndIsDeleted(DayOfWeek dayOfWeek, Boolean isDeleted);

    @Modifying
    @Query("UPDATE RestaurantClosedDayEntity e SET e.isDeleted = true, e.isActive = false, e.deletedAt = CURRENT_TIMESTAMP WHERE e.isDeleted = false")
    void softDeleteAll();
}
