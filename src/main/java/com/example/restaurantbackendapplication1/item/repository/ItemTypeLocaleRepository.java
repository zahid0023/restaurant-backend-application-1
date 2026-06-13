package com.example.restaurantbackendapplication1.item.repository;

import com.example.restaurantbackendapplication1.item.model.entity.ItemTypeLocaleEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemTypeLocaleRepository extends JpaRepository<@NonNull ItemTypeLocaleEntity, @NonNull Long> {
    Optional<ItemTypeLocaleEntity> findByItemTypeEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long itemTypeId, Long id, Boolean isActive, Boolean isDeleted);
}
