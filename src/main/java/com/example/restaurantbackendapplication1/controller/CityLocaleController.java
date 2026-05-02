package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.citylocale.CreateCityLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.citylocale.UpdateCityLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.model.entity.CityLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.CityLocaleService;
import com.example.restaurantbackendapplication1.service.CityService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cities/{city-id}/locales")
public class CityLocaleController {

    private final CityLocaleService cityLocaleService;
    private final CityService cityService;
    private final LocaleService localeService;

    public CityLocaleController(
            CityLocaleService cityLocaleService,
            CityService cityService,
            LocaleService localeService) {
        this.cityLocaleService = cityLocaleService;
        this.cityService = cityService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("city-id") Long cityId,
            @Valid @RequestBody CreateCityLocaleRequest request) {
        CityEntity cityEntity = cityService.getEntityById(cityId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cityLocaleService.create(cityEntity, localeEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("city-id") Long cityId,
            @PathVariable Long id) {
        CityEntity cityEntity = cityService.getEntityById(cityId);
        return ResponseEntity.ok(cityLocaleService.getById(id, cityEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("city-id") Long cityId,
            @Valid @ParameterObject PaginatedRequest request) {
        CityEntity cityEntity = cityService.getEntityById(cityId);
        return ResponseEntity.ok(cityLocaleService.getAll(cityEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("city-id") Long cityId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateCityLocaleRequest request) {
        CityEntity cityEntity = cityService.getEntityById(cityId);
        CityLocaleEntity entity = cityLocaleService.getEntityById(id, cityEntity);
        LocaleEntity locale = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.ok(cityLocaleService.update(entity, locale, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("city-id") Long cityId,
            @PathVariable Long id) {
        CityEntity cityEntity = cityService.getEntityById(cityId);
        CityLocaleEntity entity = cityLocaleService.getEntityById(id, cityEntity);
        return ResponseEntity.ok(cityLocaleService.delete(entity));
    }
}
