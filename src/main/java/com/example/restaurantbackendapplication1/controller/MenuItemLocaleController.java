package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.menuitemlocale.CreateMenuItemLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menuitemlocale.UpdateMenuItemLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.MenuItemLocaleService;
import com.example.restaurantbackendapplication1.service.MenuItemService;
import com.example.restaurantbackendapplication1.service.MenuSectionService;
import com.example.restaurantbackendapplication1.service.MenuService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/sections/{section-id}/items/{item-id}/locales")
public class MenuItemLocaleController {

    private final MenuItemLocaleService menuItemLocaleService;
    private final MenuItemService menuItemService;
    private final MenuSectionService menuSectionService;
    private final MenuService menuService;
    private final LocaleService localeService;

    public MenuItemLocaleController(MenuItemLocaleService menuItemLocaleService,
                                    MenuItemService menuItemService,
                                    MenuSectionService menuSectionService,
                                    MenuService menuService,
                                    LocaleService localeService) {
        this.menuItemLocaleService = menuItemLocaleService;
        this.menuItemService = menuItemService;
        this.menuSectionService = menuSectionService;
        this.menuService = menuService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable("item-id") Long itemId,
            @Valid @RequestBody CreateMenuItemLocaleRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        MenuItemEntity menuItemEntity = menuItemService.getEntityById(itemId, menuSectionEntity);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuItemLocaleService.create(menuItemEntity, localeEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable("item-id") Long itemId,
            @PathVariable Long id) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        MenuItemEntity menuItemEntity = menuItemService.getEntityById(itemId, menuSectionEntity);
        return ResponseEntity.ok(menuItemLocaleService.getById(id, menuItemEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable("item-id") Long itemId,
            @Valid @ParameterObject PaginatedRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        MenuItemEntity menuItemEntity = menuItemService.getEntityById(itemId, menuSectionEntity);
        return ResponseEntity.ok(menuItemLocaleService.getAll(menuItemEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable("item-id") Long itemId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateMenuItemLocaleRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        MenuItemEntity menuItemEntity = menuItemService.getEntityById(itemId, menuSectionEntity);
        MenuItemLocaleEntity entity = menuItemLocaleService.getEntityById(id, menuItemEntity);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.ok(menuItemLocaleService.update(entity, localeEntity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable("item-id") Long itemId,
            @PathVariable Long id) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        MenuItemEntity menuItemEntity = menuItemService.getEntityById(itemId, menuSectionEntity);
        MenuItemLocaleEntity entity = menuItemLocaleService.getEntityById(id, menuItemEntity);
        return ResponseEntity.ok(menuItemLocaleService.delete(entity));
    }
}
