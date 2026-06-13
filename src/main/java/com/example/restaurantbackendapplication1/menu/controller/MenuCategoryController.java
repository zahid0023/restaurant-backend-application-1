package com.example.restaurantbackendapplication1.menu.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menucategory.CreateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menucategory.UpdateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menucategorylocale.CreateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuTypeEntity;
import com.example.restaurantbackendapplication1.locale.service.LocaleService;
import com.example.restaurantbackendapplication1.menu.service.MenuCategoryService;
import com.example.restaurantbackendapplication1.menu.service.MenuTypeService;
import com.example.restaurantbackendapplication1.commons.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/menu-categories")
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;
    private final MenuTypeService menuTypeService;
    private final LocaleService localeService;

    public MenuCategoryController(MenuCategoryService menuCategoryService,
                                  MenuTypeService menuTypeService,
                                  LocaleService localeService) {
        this.menuCategoryService = menuCategoryService;
        this.menuTypeService = menuTypeService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateMenuCategoryRequest request) {
        MenuTypeEntity menuTypeEntity = menuTypeService.getEntityById(request.getMenuTypeId());
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), CreateMenuCategoryLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuCategoryService.create(request, menuTypeEntity, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(menuCategoryService.getById(id));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(@Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(menuCategoryService.getAll(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMenuCategoryRequest request) {
        MenuCategoryEntity entity = menuCategoryService.getEntityById(id);
        return ResponseEntity.ok(menuCategoryService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(menuCategoryService.delete(id));
    }
}
