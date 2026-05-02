package com.example.restaurantbackendapplication1.repository;

import com.example.restaurantbackendapplication1.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.model.projection.CitySummary;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CityRepository extends JpaRepository<@NonNull CityEntity, @NonNull Long> {
    Optional<CityEntity> findByIdAndIsActiveAndIsDeleted(Long id, Boolean isActive, Boolean isDeleted);

    Page<CitySummary> findAllByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted, Pageable pageable);

    List<CityEntity> findAllByIdInAndIsActiveAndIsDeleted(Set<Long> ids, Boolean isActive, Boolean isDeleted);
}
