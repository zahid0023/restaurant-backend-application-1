package com.example.restaurantbackendapplication1.menu.controller;

import com.example.restaurantbackendapplication1.menu.dto.request.menutypelocale.CreateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menutypelocale.UpdateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuTypeEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuTypeLocaleEntity;
import com.example.restaurantbackendapplication1.locale.service.LocaleService;
import com.example.restaurantbackendapplication1.menu.service.MenuTypeLocaleService;
import com.example.restaurantbackendapplication1.menu.service.MenuTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/locales")
public class MenuTypeLocaleController {

    private final MenuTypeLocaleService menuTypeLocaleService;
    private final MenuTypeService menuTypeService;
    private final LocaleService localeService;

    public MenuTypeLocaleController(MenuTypeLocaleService menuTypeLocaleService,
                                    MenuTypeService menuTypeService,
                                    LocaleService localeService) {
        this.menuTypeLocaleService = menuTypeLocaleService;
        this.menuTypeService = menuTypeService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-id") Long menuId,
            @Valid @RequestBody CreateMenuLocaleRequest request) {
        MenuTypeEntity menuTypeEntity = menuTypeService.getEntityById(menuId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuTypeLocaleService.create(menuTypeEntity, localeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateMenuLocaleRequest request) {
        MenuTypeEntity menuTypeEntity = menuTypeService.getEntityById(menuId);
        MenuTypeLocaleEntity entity = menuTypeLocaleService.getEntityById(id, menuTypeEntity);
        return ResponseEntity.ok(menuTypeLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-id") Long menuId,
            @PathVariable Long id) {
        MenuTypeEntity menuTypeEntity = menuTypeService.getEntityById(menuId);
        MenuTypeLocaleEntity entity = menuTypeLocaleService.getEntityById(id, menuTypeEntity);
        return ResponseEntity.ok(menuTypeLocaleService.delete(entity));
    }
}
