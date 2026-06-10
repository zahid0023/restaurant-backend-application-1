package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.CreateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.UpdateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.ItemCategoryLocaleService;
import com.example.restaurantbackendapplication1.service.ItemCategoryService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item-categories/{item-category-id}/locales")
public class ItemCategoryLocaleController {

    private final ItemCategoryLocaleService itemCategoryLocaleService;
    private final ItemCategoryService itemCategoryService;
    private final LocaleService localeService;

    public ItemCategoryLocaleController(
            ItemCategoryLocaleService itemCategoryLocaleService,
            ItemCategoryService itemCategoryService,
            LocaleService localeService) {
        this.itemCategoryLocaleService = itemCategoryLocaleService;
        this.itemCategoryService = itemCategoryService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("item-category-id") Long itemCategoryId,
            @Valid @RequestBody CreateItemCategoryLocaleRequest request) {
        ItemCategoryEntity itemCategoryEntity = itemCategoryService.getEntityById(itemCategoryId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemCategoryLocaleService.create(itemCategoryEntity, localeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("item-category-id") Long itemCategoryId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateItemCategoryLocaleRequest request) {
        ItemCategoryEntity itemCategoryEntity = itemCategoryService.getEntityById(itemCategoryId);
        ItemCategoryLocaleEntity entity = itemCategoryLocaleService.getEntityById(id, itemCategoryEntity);
        return ResponseEntity.ok(itemCategoryLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("item-category-id") Long itemCategoryId,
            @PathVariable Long id) {
        ItemCategoryEntity itemCategoryEntity = itemCategoryService.getEntityById(itemCategoryId);
        ItemCategoryLocaleEntity entity = itemCategoryLocaleService.getEntityById(id, itemCategoryEntity);
        return ResponseEntity.ok(itemCategoryLocaleService.delete(entity));
    }
}
