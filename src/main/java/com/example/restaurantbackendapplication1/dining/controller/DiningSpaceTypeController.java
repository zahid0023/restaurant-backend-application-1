package com.example.restaurantbackendapplication1.dining.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.diningspacetype.CreateDiningSpaceTypeRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.diningspacetype.UpdateDiningSpaceTypeRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.diningspacetypelocale.CreateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dining.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.dining.service.DiningSpaceTypeService;
import com.example.restaurantbackendapplication1.locale.service.LocaleService;
import com.example.restaurantbackendapplication1.commons.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/dining-space-types")
public class DiningSpaceTypeController {

    private final DiningSpaceTypeService diningSpaceTypeService;
    private final LocaleService localeService;

    public DiningSpaceTypeController(DiningSpaceTypeService diningSpaceTypeService,
                                     LocaleService localeService) {
        this.diningSpaceTypeService = diningSpaceTypeService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateDiningSpaceTypeRequest request) {
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), CreateDiningSpaceTypeLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED).body(diningSpaceTypeService.create(request, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(diningSpaceTypeService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(diningSpaceTypeService.getAll(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDiningSpaceTypeRequest request) {
        DiningSpaceTypeEntity entity = diningSpaceTypeService.getEntityById(id);
        return ResponseEntity.ok(diningSpaceTypeService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(diningSpaceTypeService.delete(id));
    }
}
