package com.example.restaurantbackendapplication1.item.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemcategory.CreateItemCategoryRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemcategory.UpdateItemCategoryRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemcategorylocale.CreateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.item.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.item.service.ItemCategoryService;
import com.example.restaurantbackendapplication1.locale.service.LocaleService;
import com.example.restaurantbackendapplication1.commons.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/item-categories")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;
    private final LocaleService localeService;

    public ItemCategoryController(ItemCategoryService itemCategoryService,
                                  LocaleService localeService) {
        this.itemCategoryService = itemCategoryService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateItemCategoryRequest request) {
        ItemCategoryEntity itemCategoryEntity = request.getParentId() != null
                ? itemCategoryService.getEntityById(request.getParentId()) : null;

        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), CreateItemCategoryLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemCategoryService.create(request, itemCategoryEntity, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(itemCategoryService.getById(id));
    }
    
    @GetMapping("/root")
    public ResponseEntity<?> getAllRoots(@Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(itemCategoryService.getAllRoots(request));
    }

    @GetMapping("/{item-category-id}/sub-categories")
    public ResponseEntity<?> getAllSubCategories(
            @PathVariable("item-category-id") Long itemCategoryId,
            @Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(itemCategoryService.getAllSubCategories(itemCategoryId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateItemCategoryRequest request) {
        ItemCategoryEntity entity = itemCategoryService.getEntityById(id);
        return ResponseEntity.ok(itemCategoryService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ItemCategoryEntity entity = itemCategoryService.getEntityById(id);
        return ResponseEntity.ok(itemCategoryService.delete(entity));
    }
}
