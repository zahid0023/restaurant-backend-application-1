package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.dto.request.floor.floorlocale.CreateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.floor.floorlocale.UpdateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.FloorLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.FloorLocaleService;
import com.example.restaurantbackendapplication1.service.FloorService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import jakarta.validation.Valid;
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
        var floorEntity = floorService.getEntityById(floorId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(floorLocaleService.create(floorEntity, localeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("floor-id") Long floorId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateFloorLocaleRequest request) {
        FloorLocaleEntity entity = floorLocaleService.getEntityById(floorId, id);
        return ResponseEntity.ok(floorLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("floor-id") Long floorId,
            @PathVariable Long id) {
        FloorLocaleEntity entity = floorLocaleService.getEntityById(floorId, id);
        return ResponseEntity.ok(floorLocaleService.delete(entity));
    }
}
