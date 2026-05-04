package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.MenuCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuCategoryLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuCategoryLocaleRepository extends JpaRepository<@NonNull MenuCategoryLocaleEntity, @NonNull Long> {

    Optional<MenuCategoryLocaleEntity> findByMenuCategoryEntity_IdAndIdAndIsActiveAndIsDeleted(Long menuCategoryEntityId, Long id, Boolean isActive, Boolean isDeleted);

    Page<@NonNull MenuCategoryLocaleSummary> findAllByMenuCategoryEntity_IdAndIsActiveAndIsDeleted(Long menuCategoryEntityId, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
