package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.unit.CreateUnitRequest;
import com.example.restaurantbackendapplication1.dto.request.unit.UpdateUnitRequest;
import com.example.restaurantbackendapplication1.dto.request.unitlocale.UnitLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.UnitService;
import com.example.restaurantbackendapplication1.service.UnitTypeService;
import com.example.restaurantbackendapplication1.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/units")
public class UnitController {

    private final UnitService unitService;
    private final UnitTypeService unitTypeService;
    private final LocaleService localeService;

    public UnitController(UnitService unitService,
                          UnitTypeService unitTypeService,
                          LocaleService localeService) {
        this.unitService = unitService;
        this.unitTypeService = unitTypeService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateUnitRequest request) {
        UnitTypeEntity unitTypeEntity = unitTypeService.getEntityById(request.getUnitTypeId());

        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), UnitLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(unitService.create(request, unitTypeEntity, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(unitService.getAll(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUnitRequest request) {
        UnitEntity entity = unitService.getEntityById(id);
        UnitTypeEntity unitTypeEntity = unitTypeService.getEntityById(request.getUnitTypeId());
        return ResponseEntity.ok(unitService.update(entity, request, unitTypeEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.delete(id));
    }
}
