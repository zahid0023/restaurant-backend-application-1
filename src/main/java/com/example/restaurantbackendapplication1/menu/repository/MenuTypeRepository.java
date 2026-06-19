package com.example.restaurantbackendapplication1.menu.repository;

import com.example.restaurantbackendapplication1.menu.model.entity.MenuTypeEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuTypeRepository extends JpaRepository<@NonNull MenuTypeEntity, @NonNull Long> {
    Optional<MenuTypeEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);

    <T> Page<T> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted, Pageable pageable, Class<T> type);

    List<MenuTypeEntity> findAllByIsActiveAndIsDeletedOrderBySortOrder(Boolean isActive, Boolean isDeleted);
}
