package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuItemSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<@NonNull MenuItemEntity, @NonNull Long> {
    Optional<MenuItemEntity> findByIdAndMenuSectionEntityAndIsActiveAndIsDeleted(
            Long id, MenuSectionEntity menuSectionEntity, Boolean isActive, Boolean isDeleted);

    Page<MenuItemSummary> findAllByMenuSectionEntityAndIsActiveAndIsDeleted(
            MenuSectionEntity menuSectionEntity, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
