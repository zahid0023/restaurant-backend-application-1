package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.floorlocale.CreateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.floorlocale.UpdateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.model.entity.FloorLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.FloorLocaleService;
import com.example.restaurantbackendapplication1.service.FloorService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/floors/{floor-id}/locales")
public class FloorLocaleController {

    private final FloorLocaleService floorLocaleService;
    private final FloorService floorService;
    private final LocaleService localeService;

    public FloorLocaleController(FloorLocaleService floorLocaleService,
                                 FloorService floorService,
                                 LocaleService localeService) {
        this.floorLocaleService = floorLocaleService;
        this.floorService = floorService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("floor-id") Long floorId,
            @Valid @RequestBody CreateFloorLocaleRequest request) {
        FloorEntity floorEntity = floorService.getEntityById(floorId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(floorLocaleService.create(floorEntity, localeEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("floor-id") Long floorId,
            @PathVariable Long id) {
        FloorEntity floorEntity = floorService.getEntityById(floorId);
        return ResponseEntity.ok(floorLocaleService.getById(id, floorEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("floor-id") Long floorId,
            @Valid @ParameterObject PaginatedRequest request) {
        FloorEntity floorEntity = floorService.getEntityById(floorId);
        return ResponseEntity.ok(floorLocaleService.getAll(floorEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("floor-id") Long floorId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateFloorLocaleRequest request) {
        FloorEntity floorEntity = floorService.getEntityById(floorId);
        FloorLocaleEntity entity = floorLocaleService.getEntityById(id, floorEntity);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.ok(floorLocaleService.update(entity, localeEntity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("floor-id") Long floorId,
            @PathVariable Long id) {
        FloorEntity floorEntity = floorService.getEntityById(floorId);
        FloorLocaleEntity entity = floorLocaleService.getEntityById(id, floorEntity);
        return ResponseEntity.ok(floorLocaleService.delete(entity));
    }
}
