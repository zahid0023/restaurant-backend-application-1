package com.example.restaurantbackendapplication1.menu.repository;

import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryLocaleEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuCategoryLocaleRepository extends JpaRepository<@NonNull MenuCategoryLocaleEntity, @NonNull Long> {

    Optional<MenuCategoryLocaleEntity> findByMenuCategoryEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long menuCategoryEntityId, Long id, Boolean isActive, Boolean isDeleted);
}
