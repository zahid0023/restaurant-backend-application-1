package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.citylocale.CityLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemtype.CreateItemTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.itemtype.UpdateItemTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.itemtypelocale.ItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
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
@RequestMapping("/api/v1/item-types")
public class ItemTypeController {

    private final ItemTypeService itemTypeService;
    private final LocaleService localeService;

    public ItemTypeController(ItemTypeService itemTypeService,
                              LocaleService localeService) {
        this.itemTypeService = itemTypeService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateItemTypeRequest request) {
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), ItemTypeLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemTypeService.create(request, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(itemTypeService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(itemTypeService.getAll(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateItemTypeRequest request) {
        ItemTypeEntity entity = itemTypeService.getEntityById(id);
        return ResponseEntity.ok(itemTypeService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(itemTypeService.delete(id));
    }
}
