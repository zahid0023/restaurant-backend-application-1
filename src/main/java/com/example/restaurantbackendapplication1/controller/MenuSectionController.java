package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.menusection.CreateMenuSectionRequest;
import com.example.restaurantbackendapplication1.dto.request.menusection.UpdateMenuSectionRequest;
import com.example.restaurantbackendapplication1.dto.request.menusectionlocale.MenuSectionLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.service.LocaleService;
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
@RequestMapping("/api/v1/menus/{menu-id}/sections")
public class MenuSectionController {

    private final MenuSectionService menuSectionService;
    private final MenuService menuService;
    private final LocaleService localeService;

    public MenuSectionController(MenuSectionService menuSectionService,
                                 MenuService menuService,
                                 LocaleService localeService) {
        this.menuSectionService = menuSectionService;
        this.menuService = menuService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-id") Long menuId,
            @Valid @RequestBody CreateMenuSectionRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), MenuSectionLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuSectionService.create(menuEntity, request, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-id") Long menuId,
            @PathVariable Long id) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        return ResponseEntity.ok(menuSectionService.getById(id, menuEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-id") Long menuId,
            @Valid @ParameterObject PaginatedRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        return ResponseEntity.ok(menuSectionService.getAll(menuEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateMenuSectionRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity entity = menuSectionService.getEntityById(id, menuEntity);
        return ResponseEntity.ok(menuSectionService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-id") Long menuId,
            @PathVariable Long id) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity entity = menuSectionService.getEntityById(id, menuEntity);
        return ResponseEntity.ok(menuSectionService.delete(entity));
    }
}
