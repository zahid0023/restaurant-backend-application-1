package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.unittypelocale.CreateUnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.unittypelocale.UpdateUnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeLocaleEntity;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.UnitTypeLocaleService;
import com.example.restaurantbackendapplication1.service.UnitTypeService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/unit-types/{unit-type-id}/locales")
public class UnitTypeLocaleController {

    private final UnitTypeLocaleService unitTypeLocaleService;
    private final UnitTypeService unitTypeService;
    private final LocaleService localeService;

    public UnitTypeLocaleController(
            UnitTypeLocaleService unitTypeLocaleService,
            UnitTypeService unitTypeService,
            LocaleService localeService) {
        this.unitTypeLocaleService = unitTypeLocaleService;
        this.unitTypeService = unitTypeService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("unit-type-id") Long unitTypeId,
            @Valid @RequestBody CreateUnitTypeLocaleRequest request) {
        UnitTypeEntity unitTypeEntity = unitTypeService.getEntityById(unitTypeId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(unitTypeLocaleService.create(unitTypeEntity, localeEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("unit-type-id") Long unitTypeId,
            @PathVariable Long id) {
        UnitTypeEntity unitTypeEntity = unitTypeService.getEntityById(unitTypeId);
        return ResponseEntity.ok(unitTypeLocaleService.getById(id, unitTypeEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("unit-type-id") Long unitTypeId,
            @Valid @ParameterObject PaginatedRequest request) {
        UnitTypeEntity unitTypeEntity = unitTypeService.getEntityById(unitTypeId);
        return ResponseEntity.ok(unitTypeLocaleService.getAll(unitTypeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("unit-type-id") Long unitTypeId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateUnitTypeLocaleRequest request) {
        UnitTypeEntity unitTypeEntity = unitTypeService.getEntityById(unitTypeId);
        UnitTypeLocaleEntity entity = unitTypeLocaleService.getEntityById(id, unitTypeEntity);
        LocaleEntity locale = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.ok(unitTypeLocaleService.update(entity, locale, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("unit-type-id") Long unitTypeId,
            @PathVariable Long id) {
        UnitTypeEntity unitTypeEntity = unitTypeService.getEntityById(unitTypeId);
        UnitTypeLocaleEntity entity = unitTypeLocaleService.getEntityById(id, unitTypeEntity);
        return ResponseEntity.ok(unitTypeLocaleService.delete(entity));
    }
}
