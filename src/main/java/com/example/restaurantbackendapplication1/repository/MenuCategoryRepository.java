package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuCategorySummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuCategoryRepository extends JpaRepository<@NonNull MenuCategoryEntity, @NonNull Long> {

    Optional<MenuCategoryEntity> findByIdAndIsActiveAndIsDeleted(
            Long id, Boolean isActive, Boolean isDeleted);

    Page<@NonNull MenuCategorySummary> findAllByIsActiveAndIsDeleted(
            Boolean isActive, Boolean isDeleted, Pageable pageable);
}
