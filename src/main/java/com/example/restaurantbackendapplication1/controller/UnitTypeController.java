package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.unittype.CreateUnitTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.unittype.UpdateUnitTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.unittypelocale.UnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.UnitTypeService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/unit-types")
public class UnitTypeController {

    private final UnitTypeService unitTypeService;
    private final LocaleService localeService;

    public UnitTypeController(UnitTypeService unitTypeService, LocaleService localeService) {
        this.unitTypeService = unitTypeService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateUnitTypeRequest request) {

        Set<Long> localeIds = request.getLocales().stream()
                .map(UnitTypeLocaleRequest::getLocaleId)
                .collect(Collectors.toSet());
        Map<Long, LocaleEntity> localeEntityMap = localeService.getAll(localeIds).stream()
                .collect(Collectors.toMap(LocaleEntity::getId, e -> e));

        return ResponseEntity.status(HttpStatus.CREATED).body(unitTypeService.create(request, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(unitTypeService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(unitTypeService.getAll(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUnitTypeRequest request) {
        UnitTypeEntity entity = unitTypeService.getEntityById(id);
        return ResponseEntity.ok(unitTypeService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(unitTypeService.delete(id));
    }
}
