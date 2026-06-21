package com.example.restaurantbackendapplication1.menu.repository;

import com.example.restaurantbackendapplication1.dish.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryDishEntity;
import com.example.restaurantbackendapplication1.menu.model.projection.MenuCategoryDishSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MenuCategoryDishRepository extends JpaRepository<@NonNull MenuCategoryDishEntity, @NonNull Long> {

    Optional<MenuCategoryDishEntity> findByMenuCategoryEntity_IdAndDishEntity_IdAndIsActiveAndIsDeleted(
            Long menuCategoryId, Long dishId, Boolean isActive, Boolean isDeleted);

    Page<@NonNull MenuCategoryDishSummary> findAllByMenuCategoryEntity_IdAndIsActiveAndIsDeleted(
            Long menuCategoryId, Boolean isActive, Boolean isDeleted, Pageable pageable);

    boolean existsByMenuCategoryEntity_IdAndDishEntity_IdAndIsActiveAndIsDeleted(
            Long menuCategoryId, Long dishId, Boolean isActive, Boolean isDeleted);

    @Query("SELECT mcd.dishEntity FROM MenuCategoryDishEntity mcd WHERE mcd.menuCategoryEntity.id = :menuCategoryId AND mcd.isActive = true AND mcd.isDeleted = false")
    Page<DishEntity> findDishesByMenuCategoryId(@Param("menuCategoryId") Long menuCategoryId, Pageable pageable);
}
