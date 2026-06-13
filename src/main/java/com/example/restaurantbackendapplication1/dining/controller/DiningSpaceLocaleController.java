package com.example.restaurantbackendapplication1.dining.controller;

import com.example.restaurantbackendapplication1.dining.dto.request.diningspacelocale.CreateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.diningspacelocale.UpdateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.dining.model.entity.DiningSpaceLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.dining.service.DiningSpaceLocaleService;
import com.example.restaurantbackendapplication1.dining.service.DiningSpaceService;
import com.example.restaurantbackendapplication1.locale.service.LocaleService;
import jakarta.validation.Valid;
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
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(diningSpaceLocaleService.create(
                        diningSpaceService.getEntityById(diningSpaceId), localeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("dining-space-id") Long diningSpaceId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDiningSpaceLocaleRequest request) {
        DiningSpaceLocaleEntity entity = diningSpaceLocaleService.getEntityById(diningSpaceId, id);
        return ResponseEntity.ok(diningSpaceLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("dining-space-id") Long diningSpaceId,
            @PathVariable Long id) {
        DiningSpaceLocaleEntity entity = diningSpaceLocaleService.getEntityById(diningSpaceId, id);
        return ResponseEntity.ok(diningSpaceLocaleService.delete(entity));
    }
}
