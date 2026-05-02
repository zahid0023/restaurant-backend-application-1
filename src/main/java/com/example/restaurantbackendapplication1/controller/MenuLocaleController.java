package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.menulocale.CreateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menulocale.UpdateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuLocaleEntity;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.MenuLocaleService;
import com.example.restaurantbackendapplication1.service.MenuService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/locales")
public class MenuLocaleController {

    private final MenuLocaleService menuLocaleService;
    private final MenuService menuService;
    private final LocaleService localeService;

    public MenuLocaleController(MenuLocaleService menuLocaleService,
                                MenuService menuService,
                                LocaleService localeService) {
        this.menuLocaleService = menuLocaleService;
        this.menuService = menuService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-id") Long menuId,
            @Valid @RequestBody CreateMenuLocaleRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuLocaleService.create(menuEntity, localeEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-id") Long menuId,
            @PathVariable Long id) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        return ResponseEntity.ok(menuLocaleService.getById(id, menuEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-id") Long menuId,
            @Valid @ParameterObject PaginatedRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        return ResponseEntity.ok(menuLocaleService.getAll(menuEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateMenuLocaleRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuLocaleEntity entity = menuLocaleService.getEntityById(id, menuEntity);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.ok(menuLocaleService.update(entity, localeEntity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-id") Long menuId,
            @PathVariable Long id) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuLocaleEntity entity = menuLocaleService.getEntityById(id, menuEntity);
        return ResponseEntity.ok(menuLocaleService.delete(entity));
    }
}
