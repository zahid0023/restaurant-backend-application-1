package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuLocaleRepository extends JpaRepository<@NonNull MenuLocaleEntity, @NonNull Long> {
    Optional<MenuLocaleEntity> findByIdAndMenuEntityAndIsActiveAndIsDeleted(
            Long id, MenuEntity menuEntity, Boolean isActive, Boolean isDeleted);

    Page<MenuLocaleSummary> findAllByMenuEntityAndIsActiveAndIsDeleted(
            MenuEntity menuEntity, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
