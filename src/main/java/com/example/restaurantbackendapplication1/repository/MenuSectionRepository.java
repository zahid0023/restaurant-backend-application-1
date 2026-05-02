package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuSectionSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuSectionRepository extends JpaRepository<@NonNull MenuSectionEntity, @NonNull Long> {
    Optional<MenuSectionEntity> findByIdAndMenuEntityAndIsActiveAndIsDeleted(
            Long id, MenuEntity menuEntity, Boolean isActive, Boolean isDeleted);

    Page<MenuSectionSummary> findAllByMenuEntityAndIsActiveAndIsDeleted(
            MenuEntity menuEntity, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
