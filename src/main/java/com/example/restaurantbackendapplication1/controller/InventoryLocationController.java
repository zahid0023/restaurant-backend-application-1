package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.inventorylocation.CreateInventoryLocationRequest;
import com.example.restaurantbackendapplication1.dto.request.inventorylocation.UpdateInventoryLocationRequest;
import com.example.restaurantbackendapplication1.dto.request.inventorylocationlocale.InventoryLocationLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.InventoryLocationService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/inventory-locations")
public class InventoryLocationController {

    private final InventoryLocationService inventoryLocationService;
    private final LocaleService localeService;

    public InventoryLocationController(InventoryLocationService inventoryLocationService,
                                       LocaleService localeService) {
        this.inventoryLocationService = inventoryLocationService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateInventoryLocationRequest request) {
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), InventoryLocationLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryLocationService.create(request, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryLocationService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(inventoryLocationService.getAll(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateInventoryLocationRequest request) {
        InventoryLocationEntity entity = inventoryLocationService.getEntityById(id);
        return ResponseEntity.ok(inventoryLocationService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryLocationService.delete(id));
    }
}
