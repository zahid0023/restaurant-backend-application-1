package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.CreateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.UpdateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.DiningSpaceTypeLocaleService;
import com.example.restaurantbackendapplication1.service.DiningSpaceTypeService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import jakarta.validation.Valid;
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
        var diningSpaceTypeEntity = diningSpaceTypeService.getEntityById(diningSpaceTypeId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(diningSpaceTypeLocaleService.create(diningSpaceTypeEntity, localeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("dining-space-type-id") Long diningSpaceTypeId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDiningSpaceTypeLocaleRequest request) {
        DiningSpaceTypeLocaleEntity entity = diningSpaceTypeLocaleService.getEntityById(diningSpaceTypeId, id);
        return ResponseEntity.ok(diningSpaceTypeLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("dining-space-type-id") Long diningSpaceTypeId,
            @PathVariable Long id) {
        DiningSpaceTypeLocaleEntity entity = diningSpaceTypeLocaleService.getEntityById(diningSpaceTypeId, id);
        return ResponseEntity.ok(diningSpaceTypeLocaleService.delete(entity));
    }
}
