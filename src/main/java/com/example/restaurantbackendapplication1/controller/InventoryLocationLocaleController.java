package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.inventorylocationlocale.CreateInventoryLocationLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.inventorylocationlocale.UpdateInventoryLocationLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.model.entity.InventoryLocationLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.InventoryLocationLocaleService;
import com.example.restaurantbackendapplication1.service.InventoryLocationService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory-locations/{inventory-location-id}/locales")
public class InventoryLocationLocaleController {

    private final InventoryLocationLocaleService inventoryLocationLocaleService;
    private final InventoryLocationService inventoryLocationService;
    private final LocaleService localeService;

    public InventoryLocationLocaleController(InventoryLocationLocaleService inventoryLocationLocaleService,
                                             InventoryLocationService inventoryLocationService,
                                             LocaleService localeService) {
        this.inventoryLocationLocaleService = inventoryLocationLocaleService;
        this.inventoryLocationService = inventoryLocationService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("inventory-location-id") Long inventoryLocationId,
            @Valid @RequestBody CreateInventoryLocationLocaleRequest request) {
        InventoryLocationEntity inventoryLocationEntity = inventoryLocationService.getEntityById(inventoryLocationId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryLocationLocaleService.create(inventoryLocationEntity, localeEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("inventory-location-id") Long inventoryLocationId,
            @PathVariable Long id) {
        InventoryLocationEntity inventoryLocationEntity = inventoryLocationService.getEntityById(inventoryLocationId);
        return ResponseEntity.ok(inventoryLocationLocaleService.getById(id, inventoryLocationEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("inventory-location-id") Long inventoryLocationId,
            @Valid @ParameterObject PaginatedRequest request) {
        InventoryLocationEntity inventoryLocationEntity = inventoryLocationService.getEntityById(inventoryLocationId);
        return ResponseEntity.ok(inventoryLocationLocaleService.getAll(inventoryLocationEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("inventory-location-id") Long inventoryLocationId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateInventoryLocationLocaleRequest request) {
        InventoryLocationEntity inventoryLocationEntity = inventoryLocationService.getEntityById(inventoryLocationId);
        InventoryLocationLocaleEntity entity = inventoryLocationLocaleService.getEntityById(id, inventoryLocationEntity);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.ok(inventoryLocationLocaleService.update(entity, localeEntity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("inventory-location-id") Long inventoryLocationId,
            @PathVariable Long id) {
        InventoryLocationEntity inventoryLocationEntity = inventoryLocationService.getEntityById(inventoryLocationId);
        InventoryLocationLocaleEntity entity = inventoryLocationLocaleService.getEntityById(id, inventoryLocationEntity);
        return ResponseEntity.ok(inventoryLocationLocaleService.delete(entity));
    }
}
