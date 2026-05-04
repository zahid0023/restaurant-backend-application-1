package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategorylocale.CreateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategorylocale.UpdateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.MenuCategoryLocaleService;
import com.example.restaurantbackendapplication1.service.MenuCategoryService;
import com.example.restaurantbackendapplication1.service.MenuService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/locales")
public class MenuCategoryLocaleController {

    private final MenuCategoryLocaleService menuCategoryLocaleService;
    private final MenuCategoryService menuCategoryService;
    private final MenuService menuService;
    private final LocaleService localeService;

    public MenuCategoryLocaleController(MenuCategoryLocaleService menuCategoryLocaleService,
                                        MenuCategoryService menuCategoryService,
                                        MenuService menuService,
                                        LocaleService localeService) {
        this.menuCategoryLocaleService = menuCategoryLocaleService;
        this.menuCategoryService = menuCategoryService;
        this.menuService = menuService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long categoryId,
            @Valid @RequestBody CreateMenuCategoryLocaleRequest request) {
        MenuCategoryEntity menuCategoryEntity = menuCategoryService.getEntityById(menuId, categoryId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuCategoryLocaleService.create(menuCategoryEntity, localeEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable Long id) {
        menuService.getEntityById(menuId);
        return ResponseEntity.ok(menuCategoryLocaleService.getById(menuCategoryId, id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @Valid @ParameterObject PaginatedRequest request) {
        menuService.getEntityById(menuId);
        return ResponseEntity.ok(menuCategoryLocaleService.getAll(menuCategoryId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateMenuCategoryLocaleRequest request) {
        menuService.getEntityById(menuId);
        MenuCategoryLocaleEntity entity = menuCategoryLocaleService.getEntityById(menuCategoryId, id);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.ok(menuCategoryLocaleService.update(entity, localeEntity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable Long id) {
        menuService.getEntityById(menuId);
        MenuCategoryLocaleEntity entity = menuCategoryLocaleService.getEntityById(menuCategoryId, id);
        return ResponseEntity.ok(menuCategoryLocaleService.delete(entity));
    }
}
