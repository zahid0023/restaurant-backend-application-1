package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.AssignMenuCategoryRequest;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuMenuCategoryEntity;
import com.example.restaurantbackendapplication1.service.MenuCategoryService;
import com.example.restaurantbackendapplication1.service.MenuMenuCategoryService;
import com.example.restaurantbackendapplication1.service.MenuService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menu-menu-categories")
public class MenuMenuCategoryController {

    private final MenuMenuCategoryService menuMenuCategoryService;
    private final MenuService menuService;
    private final MenuCategoryService menuCategoryService;

    public MenuMenuCategoryController(MenuMenuCategoryService menuMenuCategoryService,
                                      MenuService menuService,
                                      MenuCategoryService menuCategoryService) {
        this.menuMenuCategoryService = menuMenuCategoryService;
        this.menuService = menuService;
        this.menuCategoryService = menuCategoryService;
    }

    @PostMapping
    public ResponseEntity<?> assign(@Valid @RequestBody AssignMenuCategoryRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(request.getMenuId());
        MenuCategoryEntity menuCategoryEntity = menuCategoryService.getEntityById(request.getMenuCategoryId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuMenuCategoryService.assign(menuEntity, menuCategoryEntity));
    }

    @GetMapping("/{menu-id}/menu-categories")
    public ResponseEntity<?> getAllMenuCategories(
            @PathVariable("menu-id") Long menuId,
            @Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(menuMenuCategoryService.getAllMenuCategories(menuId, request));
    }

    @DeleteMapping("/{menu-id}/{menu-category-id}")
    public ResponseEntity<?> unassign(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuCategoryEntity menuCategoryEntity = menuCategoryService.getEntityById(menuCategoryId);
        MenuMenuCategoryEntity entity = menuMenuCategoryService.getAssignment(menuEntity, menuCategoryEntity);
        return ResponseEntity.ok(menuMenuCategoryService.unassign(entity));
    }
}
