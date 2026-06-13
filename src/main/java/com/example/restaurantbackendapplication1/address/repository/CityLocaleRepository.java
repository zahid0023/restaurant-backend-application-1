package com.example.restaurantbackendapplication1.address.repository;

import com.example.restaurantbackendapplication1.address.model.entity.CityLocaleEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityLocaleRepository extends JpaRepository<@NonNull CityLocaleEntity, @NonNull Long> {
    Optional<CityLocaleEntity> findByCityEntity_IdAndIdAndIsActiveAndIsDeleted(
            Long cityId, Long id, Boolean isActive, Boolean isDeleted);
}
