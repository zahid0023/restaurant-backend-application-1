package com.example.restaurantbackendapplication1.item.repository;

import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.item.model.projection.ItemSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemRepository extends JpaRepository<@NonNull ItemEntity, @NonNull Long> {
    Optional<ItemEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);

    List<ItemEntity> findAllByIdInAndIsActiveAndIsDeleted(Set<Long> ids, Boolean isActive, Boolean isDeleted);

    Page<@NonNull ItemSummary> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted, Pageable pageable);

    Page<@NonNull ItemSummary> findAllByItemTypeEntity_IdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted, Pageable pageable);

    @Query(value = "SELECT DISTINCT i FROM ItemEntity i LEFT JOIN i.itemLocaleEntities l " +
                   "WHERE i.isActive = true AND i.isDeleted = false " +
                   "AND (LOWER(i.code) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(l.name) LIKE LOWER(CONCAT('%', :query, '%')))",
           countQuery = "SELECT COUNT(DISTINCT i) FROM ItemEntity i LEFT JOIN i.itemLocaleEntities l " +
                        "WHERE i.isActive = true AND i.isDeleted = false " +
                        "AND (LOWER(i.code) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(l.name) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<@NonNull ItemSummary> searchByQuery(@Param("query") String query, Pageable pageable);
}
