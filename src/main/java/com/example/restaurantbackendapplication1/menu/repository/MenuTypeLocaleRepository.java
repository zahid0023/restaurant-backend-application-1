package com.example.restaurantbackendapplication1.menu.repository;

import com.example.restaurantbackendapplication1.menu.model.entity.MenuTypeEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuTypeLocaleEntity;
import com.example.restaurantbackendapplication1.menu.model.projection.MenuTypeLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuTypeLocaleRepository extends JpaRepository<@NonNull MenuTypeLocaleEntity, @NonNull Long> {
    Optional<MenuTypeLocaleEntity> findByIdAndMenuTypeEntityAndIsActiveAndIsDeleted(
            Long id, MenuTypeEntity menuTypeEntity, Boolean isActive, Boolean isDeleted);

    Page<@NonNull MenuTypeLocaleSummary> findAllByMenuTypeEntityAndIsActiveAndIsDeleted(
            MenuTypeEntity menuTypeEntity, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
