package com.example.restaurantbackendapplication1.item.controller;

import com.example.restaurantbackendapplication1.item.dto.request.itemtypelocale.CreateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemtypelocale.UpdateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.item.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemTypeLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.item.service.ItemTypeLocaleService;
import com.example.restaurantbackendapplication1.item.service.ItemTypeService;
import com.example.restaurantbackendapplication1.locale.service.LocaleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item-types/{item-type-id}/locales")
public class ItemTypeLocaleController {

    private final ItemTypeService itemTypeService;
    private final ItemTypeLocaleService itemTypeLocaleService;
    private final LocaleService localeService;

    public ItemTypeLocaleController(ItemTypeService itemTypeService,
                                    ItemTypeLocaleService itemTypeLocaleService,
                                    LocaleService localeService) {
        this.itemTypeService = itemTypeService;
        this.itemTypeLocaleService = itemTypeLocaleService;
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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("item-type-id") Long itemTypeId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateItemTypeLocaleRequest request) {
        ItemTypeLocaleEntity entity = itemTypeLocaleService.getEntityById(itemTypeId, id);
        return ResponseEntity.ok(itemTypeLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("item-type-id") Long itemTypeId,
            @PathVariable Long id) {
        ItemTypeLocaleEntity entity = itemTypeLocaleService.getEntityById(itemTypeId, id);
        return ResponseEntity.ok(itemTypeLocaleService.delete(entity));
    }
}
