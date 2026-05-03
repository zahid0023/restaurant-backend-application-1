package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategory.CreateItemCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategory.UpdateItemCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.ItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.ItemCategoryService;
import com.example.restaurantbackendapplication1.service.ItemTypeService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/item-types/{item-type-id}/item-categories")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;
    private final ItemTypeService itemTypeService;
    private final LocaleService localeService;

    public ItemCategoryController(ItemCategoryService itemCategoryService,
                                  ItemTypeService itemTypeService,
                                  LocaleService localeService) {
        this.itemCategoryService = itemCategoryService;
        this.itemTypeService = itemTypeService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("item-type-id") Long itemTypeId,
            @Valid @RequestBody CreateItemCategoryRequest request) {
        ItemTypeEntity itemTypeEntity = itemTypeService.getEntityById(itemTypeId);

        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), ItemCategoryLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemCategoryService.create(request, itemTypeEntity, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("item-type-id") Long itemTypeId,
            @PathVariable Long id) {
        return ResponseEntity.ok(itemCategoryService.getById(itemTypeId, id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("item-type-id") Long itemTypeId,
            @Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(itemCategoryService.getAll(itemTypeId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("item-type-id") Long itemTypeId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateItemCategoryRequest request) {
        ItemCategoryEntity entity = itemCategoryService.getEntityById(itemTypeId, id);
        return ResponseEntity.ok(itemCategoryService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("item-type-id") Long itemTypeId,
            @PathVariable Long id) {
        return ResponseEntity.ok(itemCategoryService.delete(itemTypeId, id));
    }
}
