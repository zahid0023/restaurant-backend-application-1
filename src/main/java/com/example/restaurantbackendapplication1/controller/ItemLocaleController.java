package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.dto.request.itemlocale.CreateItemLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemlocale.UpdateItemLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.ItemLocaleService;
import com.example.restaurantbackendapplication1.service.ItemService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/items/{item-id}/locales")
public class ItemLocaleController {

    private final ItemLocaleService itemLocaleService;
    private final ItemService itemService;
    private final LocaleService localeService;

    public ItemLocaleController(ItemLocaleService itemLocaleService,
                                ItemService itemService,
                                LocaleService localeService) {
        this.itemLocaleService = itemLocaleService;
        this.itemService = itemService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("item-id") Long itemId,
            @Valid @RequestBody CreateItemLocaleRequest request) {
        ItemEntity itemEntity = itemService.getEntityById(itemId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemLocaleService.create(itemEntity, localeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("item-id") Long itemId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateItemLocaleRequest request) {
        ItemEntity itemEntity = itemService.getEntityById(itemId);
        ItemLocaleEntity entity = itemLocaleService.getEntityById(id, itemEntity);
        return ResponseEntity.ok(itemLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("item-id") Long itemId,
            @PathVariable Long id) {
        ItemEntity itemEntity = itemService.getEntityById(itemId);
        ItemLocaleEntity entity = itemLocaleService.getEntityById(id, itemEntity);
        return ResponseEntity.ok(itemLocaleService.delete(entity));
    }
}
