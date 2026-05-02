package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.model.entity.CountryLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.CountryLocaleSummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryLocaleRepository extends JpaRepository<@NonNull CountryLocaleEntity, @NonNull Long> {
    Optional<CountryLocaleEntity> findByIdAndCountryEntityAndIsActiveAndIsDeleted(
            Long id, CountryEntity country, Boolean isActive, Boolean isDeleted);

    Page<CountryLocaleSummary> findAllByCountryEntityAndIsActiveAndIsDeleted(
            CountryEntity country, Boolean isActive, Boolean isDeleted, Pageable pageable);
}
