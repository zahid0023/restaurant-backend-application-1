package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuSectionLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuSectionLocaleRepository extends JpaRepository<@NonNull MenuSectionLocaleEntity, @NonNull Long> {
    Optional<MenuSectionLocaleEntity> findByIdAndMenuSectionEntityAndIsActiveAndIsDeleted(
            Long id, MenuSectionEntity menuSectionEntity, Boolean isActive, Boolean isDeleted);

    Page<MenuSectionLocaleSummary> findAllByMenuSectionEntityAndIsActiveAndIsDeleted(
            MenuSectionEntity menuSectionEntity, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
