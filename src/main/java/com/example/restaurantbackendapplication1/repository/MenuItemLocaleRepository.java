package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuItemLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuItemLocaleRepository extends JpaRepository<@NonNull MenuItemLocaleEntity, @NonNull Long> {
    Optional<MenuItemLocaleEntity> findByIdAndMenuItemEntityAndIsActiveAndIsDeleted(
            Long id, MenuItemEntity menuItemEntity, Boolean isActive, Boolean isDeleted);

    Page<MenuItemLocaleSummary> findAllByMenuItemEntityAndIsActiveAndIsDeleted(
            MenuItemEntity menuItemEntity, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
