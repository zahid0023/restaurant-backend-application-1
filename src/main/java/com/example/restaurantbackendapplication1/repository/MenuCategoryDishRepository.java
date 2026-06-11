package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.MenuCategoryDishEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuCategoryDishSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuCategoryDishRepository extends JpaRepository<@NonNull MenuCategoryDishEntity, @NonNull Long> {

    Optional<MenuCategoryDishEntity> findByMenuCategoryEntity_IdAndDishEntity_IdAndIsActiveAndIsDeleted(
            Long menuCategoryId, Long dishId, Boolean isActive, Boolean isDeleted);

    Page<@NonNull MenuCategoryDishSummary> findAllByMenuCategoryEntity_IdAndIsActiveAndIsDeleted(
            Long menuCategoryId, Boolean isActive, Boolean isDeleted, Pageable pageable);

    boolean existsByMenuCategoryEntity_IdAndDishEntity_IdAndIsActiveAndIsDeleted(
            Long menuCategoryId, Long dishId, Boolean isActive, Boolean isDeleted);
}
