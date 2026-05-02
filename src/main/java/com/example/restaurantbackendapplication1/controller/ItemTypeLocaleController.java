package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.itemtypelocale.CreateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemtypelocale.UpdateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.ItemTypeLocaleService;
import com.example.restaurantbackendapplication1.service.ItemTypeService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item-types/{item-type-id}/locales")
public class ItemTypeLocaleController {

    private final ItemTypeLocaleService itemTypeLocaleService;
    private final ItemTypeService itemTypeService;
    private final LocaleService localeService;

    public ItemTypeLocaleController(
            ItemTypeLocaleService itemTypeLocaleService,
            ItemTypeService itemTypeService,
            LocaleService localeService) {
        this.itemTypeLocaleService = itemTypeLocaleService;
        this.itemTypeService = itemTypeService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("item-type-id") Long itemTypeId,
            @Valid @RequestBody CreateItemTypeLocaleRequest request) {
        ItemTypeEntity itemTypeEntity = itemTypeService.getEntityById(itemTypeId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemTypeLocaleService.create(itemTypeEntity, localeEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("item-type-id") Long itemTypeId,
            @PathVariable Long id) {
        ItemTypeEntity itemTypeEntity = itemTypeService.getEntityById(itemTypeId);
        return ResponseEntity.ok(itemTypeLocaleService.getById(id, itemTypeEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("item-type-id") Long itemTypeId,
            @Valid @ParameterObject PaginatedRequest request) {
        ItemTypeEntity itemTypeEntity = itemTypeService.getEntityById(itemTypeId);
        return ResponseEntity.ok(itemTypeLocaleService.getAll(itemTypeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("item-type-id") Long itemTypeId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateItemTypeLocaleRequest request) {
        ItemTypeEntity itemTypeEntity = itemTypeService.getEntityById(itemTypeId);
        ItemTypeLocaleEntity entity = itemTypeLocaleService.getEntityById(id, itemTypeEntity);
        LocaleEntity locale = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.ok(itemTypeLocaleService.update(entity, locale, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("item-type-id") Long itemTypeId,
            @PathVariable Long id) {
        ItemTypeEntity itemTypeEntity = itemTypeService.getEntityById(itemTypeId);
        ItemTypeLocaleEntity entity = itemTypeLocaleService.getEntityById(id, itemTypeEntity);
        return ResponseEntity.ok(itemTypeLocaleService.delete(entity));
    }
}
