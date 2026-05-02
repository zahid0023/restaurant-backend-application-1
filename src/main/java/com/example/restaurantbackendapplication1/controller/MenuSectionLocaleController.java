package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.menusectionlocale.CreateMenuSectionLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menusectionlocale.UpdateMenuSectionLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionLocaleEntity;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.MenuSectionLocaleService;
import com.example.restaurantbackendapplication1.service.MenuSectionService;
import com.example.restaurantbackendapplication1.service.MenuService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/sections/{section-id}/locales")
public class MenuSectionLocaleController {

    private final MenuSectionLocaleService menuSectionLocaleService;
    private final MenuSectionService menuSectionService;
    private final MenuService menuService;
    private final LocaleService localeService;

    public MenuSectionLocaleController(MenuSectionLocaleService menuSectionLocaleService,
                                       MenuSectionService menuSectionService,
                                       MenuService menuService,
                                       LocaleService localeService) {
        this.menuSectionLocaleService = menuSectionLocaleService;
        this.menuSectionService = menuSectionService;
        this.menuService = menuService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @Valid @RequestBody CreateMenuSectionLocaleRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuSectionLocaleService.create(menuSectionEntity, localeEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable Long id) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        return ResponseEntity.ok(menuSectionLocaleService.getById(id, menuSectionEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @Valid @ParameterObject PaginatedRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        return ResponseEntity.ok(menuSectionLocaleService.getAll(menuSectionEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateMenuSectionLocaleRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        MenuSectionLocaleEntity entity = menuSectionLocaleService.getEntityById(id, menuSectionEntity);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.ok(menuSectionLocaleService.update(entity, localeEntity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable Long id) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        MenuSectionLocaleEntity entity = menuSectionLocaleService.getEntityById(id, menuSectionEntity);
        return ResponseEntity.ok(menuSectionLocaleService.delete(entity));
    }
}
