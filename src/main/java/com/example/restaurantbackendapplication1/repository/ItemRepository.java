package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.projection.ItemSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemRepository extends JpaRepository<@NonNull ItemEntity, @NonNull Long> {
    Optional<ItemEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);

    List<ItemEntity> findAllByIdInAndIsActiveAndIsDeleted(Set<Long> ids, Boolean isActive, Boolean isDeleted);

    Page<@NonNull ItemSummary> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted, Pageable pageable);
}
