package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.item.CreateItemRequest;
import com.example.restaurantbackendapplication1.dto.request.item.UpdateItemRequest;
import com.example.restaurantbackendapplication1.dto.request.itemlocale.CreateItemLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.*;
import com.example.restaurantbackendapplication1.service.*;
import com.example.restaurantbackendapplication1.utils.LocaleUtils;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemTypeService itemTypeService;
    private final UnitTypeService unitTypeService;
    private final LocaleService localeService;

    public ItemController(ItemService itemService,
                          ItemTypeService itemTypeService,
                          UnitTypeService unitTypeService,
                          LocaleService localeService) {
        this.itemService = itemService;
        this.itemTypeService = itemTypeService;
        this.unitTypeService = unitTypeService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateItemRequest request) {
        ItemTypeEntity itemTypeEntity = itemTypeService.getEntityById(request.getItemTypeId());
        UnitTypeEntity unitTypeEntity = unitTypeService.getEntityById(request.getUnitTypeId());

        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), CreateItemLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemService.create(request, itemTypeEntity, unitTypeEntity, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@Parameter(description = "Item ID") @PathVariable Long id) {
        return ResponseEntity.ok(itemService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String query,
            @Valid @ParameterObject PaginatedRequest request) {
        if (query != null && !query.isBlank()) {
            return ResponseEntity.ok(itemService.search(query, request));
        }
        return ResponseEntity.ok(itemService.getAll(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateItemRequest request) {
        ItemEntity entity = itemService.getEntityById(id);
        return ResponseEntity.ok(itemService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.delete(id));
    }
}
