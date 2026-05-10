package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.CreateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.UpdateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.DiningSpaceLocaleService;
import com.example.restaurantbackendapplication1.service.DiningSpaceService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dining-spaces/{dining-space-id}/locales")
public class DiningSpaceLocaleController {

    private final DiningSpaceLocaleService diningSpaceLocaleService;
    private final DiningSpaceService diningSpaceService;
    private final LocaleService localeService;

    public DiningSpaceLocaleController(DiningSpaceLocaleService diningSpaceLocaleService,
                                       DiningSpaceService diningSpaceService,
                                       LocaleService localeService) {
        this.diningSpaceLocaleService = diningSpaceLocaleService;
        this.diningSpaceService = diningSpaceService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("dining-space-id") Long diningSpaceId,
            @Valid @RequestBody CreateDiningSpaceLocaleRequest request) {
        DiningSpaceEntity diningSpaceEntity = diningSpaceService.getEntityById(diningSpaceId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(diningSpaceLocaleService.create(diningSpaceEntity, localeEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("dining-space-id") Long diningSpaceId,
            @PathVariable Long id) {
        DiningSpaceEntity diningSpaceEntity = diningSpaceService.getEntityById(diningSpaceId);
        return ResponseEntity.ok(diningSpaceLocaleService.getById(id, diningSpaceEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("dining-space-id") Long diningSpaceId,
            @Valid @ParameterObject PaginatedRequest request) {
        DiningSpaceEntity diningSpaceEntity = diningSpaceService.getEntityById(diningSpaceId);
        return ResponseEntity.ok(diningSpaceLocaleService.getAll(diningSpaceEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("dining-space-id") Long diningSpaceId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDiningSpaceLocaleRequest request) {
        DiningSpaceEntity diningSpaceEntity = diningSpaceService.getEntityById(diningSpaceId);
        DiningSpaceLocaleEntity entity = diningSpaceLocaleService.getEntityById(id, diningSpaceEntity);
        LocaleEntity locale = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.ok(diningSpaceLocaleService.update(entity, locale, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("dining-space-id") Long diningSpaceId,
            @PathVariable Long id) {
        DiningSpaceEntity diningSpaceEntity = diningSpaceService.getEntityById(diningSpaceId);
        DiningSpaceLocaleEntity entity = diningSpaceLocaleService.getEntityById(id, diningSpaceEntity);
        return ResponseEntity.ok(diningSpaceLocaleService.delete(entity));
    }
}
