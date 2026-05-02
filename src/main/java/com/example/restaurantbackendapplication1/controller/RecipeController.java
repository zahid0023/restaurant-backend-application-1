package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.recipe.CreateRecipeRequest;
import com.example.restaurantbackendapplication1.dto.request.recipe.UpdateRecipeRequest;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.entity.RecipeEntity;
import com.example.restaurantbackendapplication1.service.MenuItemService;
import com.example.restaurantbackendapplication1.service.MenuSectionService;
import com.example.restaurantbackendapplication1.service.MenuService;
import com.example.restaurantbackendapplication1.service.RecipeService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/sections/{section-id}/items/{item-id}/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final MenuItemService menuItemService;
    private final MenuSectionService menuSectionService;
    private final MenuService menuService;

    public RecipeController(RecipeService recipeService,
                            MenuItemService menuItemService,
                            MenuSectionService menuSectionService,
                            MenuService menuService) {
        this.recipeService = recipeService;
        this.menuItemService = menuItemService;
        this.menuSectionService = menuSectionService;
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable("item-id") Long itemId,
            @Valid @RequestBody CreateRecipeRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        MenuItemEntity menuItemEntity = menuItemService.getEntityById(itemId, menuSectionEntity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recipeService.create(menuItemEntity, request));
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
        return ResponseEntity.ok(recipeService.getById(id, menuItemEntity));
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
        return ResponseEntity.ok(recipeService.getAll(menuItemEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable("item-id") Long itemId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateRecipeRequest request) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        MenuItemEntity menuItemEntity = menuItemService.getEntityById(itemId, menuSectionEntity);
        RecipeEntity entity = recipeService.getEntityById(id, menuItemEntity);
        return ResponseEntity.ok(recipeService.update(entity, request));
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
        RecipeEntity entity = recipeService.getEntityById(id, menuItemEntity);
        return ResponseEntity.ok(recipeService.delete(entity));
    }
}
