package com.example.restaurantbackendapplication1.menu.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menutype.CreateMenuTypeRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menutype.UpdateMenuTypeRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menutypelocale.CreateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuTypeEntity;
import com.example.restaurantbackendapplication1.menu.model.enums.MenuTypeDetailLevel;
import com.example.restaurantbackendapplication1.locale.service.LocaleService;
import com.example.restaurantbackendapplication1.menu.service.MenuTypeService;
import com.example.restaurantbackendapplication1.commons.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/menus")
public class MenuTypeController {

    private final MenuTypeService menuTypeService;
    private final LocaleService localeService;

    public MenuTypeController(MenuTypeService menuTypeService, LocaleService localeService) {
        this.menuTypeService = menuTypeService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateMenuTypeRequest request) {
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), CreateMenuLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuTypeService.create(request, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(menuTypeService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @Valid @ParameterObject PaginatedRequest request,
            @RequestParam(defaultValue = "BASIC") MenuTypeDetailLevel detail) {
        return switch (detail) {
            case WITH_CATEGORIES -> ResponseEntity.ok(menuTypeService.getAllWithCategories(request));
            case FULL -> ResponseEntity.ok(menuTypeService.getAllFull(request));
            default -> ResponseEntity.ok(menuTypeService.getAll(request));
        };
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMenuTypeRequest request) {
        MenuTypeEntity entity = menuTypeService.getEntityById(id);
        return ResponseEntity.ok(menuTypeService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        MenuTypeEntity entity = menuTypeService.getEntityById(id);
        return ResponseEntity.ok(menuTypeService.delete(entity));
    }
}
