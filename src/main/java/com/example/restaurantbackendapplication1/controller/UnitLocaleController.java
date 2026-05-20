package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.dto.request.unitlocale.CreateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.unitlocale.UpdateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitLocaleEntity;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.UnitLocaleService;
import com.example.restaurantbackendapplication1.service.UnitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/unit-types/{unit-type-id}/units/{unit-id}/locales")
public class UnitLocaleController {

    private final UnitLocaleService unitLocaleService;
    private final UnitService unitService;
    private final LocaleService localeService;

    public UnitLocaleController(UnitLocaleService unitLocaleService,
                                UnitService unitService,
                                LocaleService localeService) {
        this.unitLocaleService = unitLocaleService;
        this.unitService = unitService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("unit-type-id") Long unitTypeId,
            @PathVariable("unit-id") Long unitId,
            @Valid @RequestBody CreateUnitLocaleRequest request) {
        UnitEntity unitEntity = unitService.getEntityById(unitTypeId, unitId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(unitLocaleService.create(unitEntity, localeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("unit-type-id") Long unitTypeId,
            @PathVariable("unit-id") Long unitId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateUnitLocaleRequest request) {
        UnitEntity unitEntity = unitService.getEntityById(unitTypeId, unitId);
        UnitLocaleEntity entity = unitLocaleService.getEntityById(id, unitEntity);
        return ResponseEntity.ok(unitLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("unit-type-id") Long unitTypeId,
            @PathVariable("unit-id") Long unitId,
            @PathVariable Long id) {
        UnitEntity unitEntity = unitService.getEntityById(unitTypeId, unitId);
        UnitLocaleEntity entity = unitLocaleService.getEntityById(id, unitEntity);
        return ResponseEntity.ok(unitLocaleService.delete(entity));
    }
}
