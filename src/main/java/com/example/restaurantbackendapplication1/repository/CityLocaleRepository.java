package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.model.entity.CityLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.CityLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityLocaleRepository extends JpaRepository<@NonNull CityLocaleEntity, @NonNull Long> {
    Optional<CityLocaleEntity> findByIdAndCityEntityAndIsActiveAndIsDeleted(
            Long id, CityEntity city, Boolean isActive, Boolean isDeleted);

    Page<@NonNull CityLocaleSummary> findAllByCityEntityAndIsActiveAndIsDeleted(
            CityEntity city, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
