package com.example.restaurantbackendapplication1.menu.controller;

import com.example.restaurantbackendapplication1.menu.dto.request.menucategorylocale.CreateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menucategorylocale.UpdateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.locale.service.LocaleService;
import com.example.restaurantbackendapplication1.menu.service.MenuCategoryLocaleService;
import com.example.restaurantbackendapplication1.menu.service.MenuCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menu-categories/{menu-category-id}/locales")
public class MenuCategoryLocaleController {

    private final MenuCategoryLocaleService menuCategoryLocaleService;
    private final MenuCategoryService menuCategoryService;
    private final LocaleService localeService;

    public MenuCategoryLocaleController(MenuCategoryLocaleService menuCategoryLocaleService,
                                        MenuCategoryService menuCategoryService,
                                        LocaleService localeService) {
        this.menuCategoryLocaleService = menuCategoryLocaleService;
        this.menuCategoryService = menuCategoryService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @Valid @RequestBody CreateMenuCategoryLocaleRequest request) {
        MenuCategoryEntity menuCategoryEntity = menuCategoryService.getEntityById(menuCategoryId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuCategoryLocaleService.create(menuCategoryEntity, localeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateMenuCategoryLocaleRequest request) {
        MenuCategoryLocaleEntity entity = menuCategoryLocaleService.getEntityById(menuCategoryId, id);
        return ResponseEntity.ok(menuCategoryLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable Long id) {
        MenuCategoryLocaleEntity entity = menuCategoryLocaleService.getEntityById(menuCategoryId, id);
        return ResponseEntity.ok(menuCategoryLocaleService.delete(entity));
    }
}
