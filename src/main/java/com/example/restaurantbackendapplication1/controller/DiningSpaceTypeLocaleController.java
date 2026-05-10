package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.CreateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.UpdateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.DiningSpaceTypeLocaleService;
import com.example.restaurantbackendapplication1.service.DiningSpaceTypeService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dining-space-types/{dining-space-type-id}/locales")
public class DiningSpaceTypeLocaleController {

    private final DiningSpaceTypeLocaleService diningSpaceTypeLocaleService;
    private final DiningSpaceTypeService diningSpaceTypeService;
    private final LocaleService localeService;

    public DiningSpaceTypeLocaleController(
            DiningSpaceTypeLocaleService diningSpaceTypeLocaleService,
            DiningSpaceTypeService diningSpaceTypeService,
            LocaleService localeService) {
        this.diningSpaceTypeLocaleService = diningSpaceTypeLocaleService;
        this.diningSpaceTypeService = diningSpaceTypeService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("dining-space-type-id") Long diningSpaceTypeId,
            @Valid @RequestBody CreateDiningSpaceTypeLocaleRequest request) {
        DiningSpaceTypeEntity diningSpaceTypeEntity = diningSpaceTypeService.getEntityById(diningSpaceTypeId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(diningSpaceTypeLocaleService.create(diningSpaceTypeEntity, localeEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("dining-space-type-id") Long diningSpaceTypeId,
            @PathVariable Long id) {
        DiningSpaceTypeEntity diningSpaceTypeEntity = diningSpaceTypeService.getEntityById(diningSpaceTypeId);
        return ResponseEntity.ok(diningSpaceTypeLocaleService.getById(id, diningSpaceTypeEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("dining-space-type-id") Long diningSpaceTypeId,
            @Valid @ParameterObject PaginatedRequest request) {
        DiningSpaceTypeEntity diningSpaceTypeEntity = diningSpaceTypeService.getEntityById(diningSpaceTypeId);
        return ResponseEntity.ok(diningSpaceTypeLocaleService.getAll(diningSpaceTypeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("dining-space-type-id") Long diningSpaceTypeId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDiningSpaceTypeLocaleRequest request) {
        DiningSpaceTypeEntity diningSpaceTypeEntity = diningSpaceTypeService.getEntityById(diningSpaceTypeId);
        DiningSpaceTypeLocaleEntity entity = diningSpaceTypeLocaleService.getEntityById(id, diningSpaceTypeEntity);
        LocaleEntity locale = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.ok(diningSpaceTypeLocaleService.update(entity, locale, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("dining-space-type-id") Long diningSpaceTypeId,
            @PathVariable Long id) {
        DiningSpaceTypeEntity diningSpaceTypeEntity = diningSpaceTypeService.getEntityById(diningSpaceTypeId);
        DiningSpaceTypeLocaleEntity entity = diningSpaceTypeLocaleService.getEntityById(id, diningSpaceTypeEntity);
        return ResponseEntity.ok(diningSpaceTypeLocaleService.delete(entity));
    }
}
