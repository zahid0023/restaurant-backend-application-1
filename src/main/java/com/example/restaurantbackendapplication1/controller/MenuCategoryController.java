package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.CreateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.UpdateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategorylocale.MenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.MenuCategoryService;
import com.example.restaurantbackendapplication1.service.MenuService;
import com.example.restaurantbackendapplication1.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/menu-categories")
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;
    private final MenuService menuService;
    private final LocaleService localeService;

    public MenuCategoryController(MenuCategoryService menuCategoryService,
                                  MenuService menuService,
                                  LocaleService localeService) {
        this.menuCategoryService = menuCategoryService;
        this.menuService = menuService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-id") Long menuId,
            @Valid @RequestBody CreateMenuCategoryRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(request.getLocales(), MenuCategoryLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuCategoryService.create(request, menuEntity, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-id") Long menuId,
            @PathVariable Long id) {
        return ResponseEntity.ok(menuCategoryService.getById(menuId, id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-id") Long menuId,
            @Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(menuCategoryService.getAll(menuId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateMenuCategoryRequest request) {
        MenuCategoryEntity entity = menuCategoryService.getEntityById(menuId, id);
        return ResponseEntity.ok(menuCategoryService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-id") Long menuId,
            @PathVariable Long id) {
        return ResponseEntity.ok(menuCategoryService.delete(menuId, id));
    }
}
