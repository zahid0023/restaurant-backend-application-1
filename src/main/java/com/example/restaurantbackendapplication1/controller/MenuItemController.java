package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.menuitem.CreateMenuItemRequest;
import com.example.restaurantbackendapplication1.dto.request.menuitem.UpdateMenuItemRequest;
import com.example.restaurantbackendapplication1.dto.request.menuitemlocale.MenuItemLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.MenuItemService;
import com.example.restaurantbackendapplication1.service.MenuSectionService;
import com.example.restaurantbackendapplication1.service.MenuService;
import com.example.restaurantbackendapplication1.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/sections/{section-id}/items")
public class MenuItemController {

    private final MenuItemService menuItemService;
    private final MenuSectionService menuSectionService;
    private final MenuService menuService;
    private final LocaleService localeService;

    public MenuItemController(MenuItemService menuItemService,
                              MenuSectionService menuSectionService,
                              MenuService menuService,
                              LocaleService localeService) {
        this.menuItemService = menuItemService;
        this.menuSectionService = menuSectionService;
        this.menuService = menuService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @Valid @RequestBody CreateMenuItemRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), MenuItemLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuItemService.create(menuSectionEntity, request, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable Long id) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        return ResponseEntity.ok(menuItemService.getById(id, menuSectionEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @Valid @ParameterObject PaginatedRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        return ResponseEntity.ok(menuItemService.getAll(menuSectionEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateMenuItemRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        MenuItemEntity entity = menuItemService.getEntityById(id, menuSectionEntity);
        return ResponseEntity.ok(menuItemService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable Long id) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        MenuItemEntity entity = menuItemService.getEntityById(id, menuSectionEntity);
        return ResponseEntity.ok(menuItemService.delete(entity));
    }
}
