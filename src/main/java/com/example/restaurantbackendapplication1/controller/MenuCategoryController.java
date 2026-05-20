package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.CreateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.UpdateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.menucategorylocale.CreateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.MenuCategoryService;
import com.example.restaurantbackendapplication1.utils.LocaleUtils;
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
    private final LocaleService localeService;

    public MenuCategoryController(MenuCategoryService menuCategoryService,
                                  LocaleService localeService) {
        this.menuCategoryService = menuCategoryService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateMenuCategoryRequest request) {
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), CreateMenuCategoryLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuCategoryService.create(request, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(menuCategoryService.getById(id));
    }

    @GetMapping
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
