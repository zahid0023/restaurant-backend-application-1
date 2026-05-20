package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuMenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuCategorySummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MenuMenuCategoryRepository extends JpaRepository<@NonNull MenuMenuCategoryEntity, @NonNull Long> {

    Optional<MenuMenuCategoryEntity> findByMenuEntityAndMenuCategoryEntityAndIsActiveAndIsDeleted(
            MenuEntity menuEntity, MenuCategoryEntity menuCategoryEntity, Boolean isActive, Boolean isDeleted);

    boolean existsByMenuEntityAndMenuCategoryEntityAndIsActiveAndIsDeleted(
            MenuEntity menuEntity, MenuCategoryEntity menuCategoryEntity, Boolean isActive, Boolean isDeleted);

    @Query(value = "SELECT mc FROM MenuMenuCategoryEntity mmc JOIN mmc.menuCategoryEntity mc " +
                   "WHERE mmc.menuEntity.id = :menuId AND mmc.isActive = :isActive AND mmc.isDeleted = :isDeleted",
           countQuery = "SELECT COUNT(mmc) FROM MenuMenuCategoryEntity mmc " +
                        "WHERE mmc.menuEntity.id = :menuId AND mmc.isActive = :isActive AND mmc.isDeleted = :isDeleted")
    Page<@NonNull MenuCategorySummary> findAllMenuCategoriesByMenuId(
            @Param("menuId") Long menuId,
            @Param("isActive") Boolean isActive,
            @Param("isDeleted") Boolean isDeleted,
            Pageable pageable);
}
