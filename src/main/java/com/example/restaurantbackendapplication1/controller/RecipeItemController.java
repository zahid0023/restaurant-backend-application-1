package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.recipeitem.CreateRecipeItemRequest;
import com.example.restaurantbackendapplication1.dto.request.recipeitem.UpdateRecipeItemRequest;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.entity.RecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.RecipeItemEntity;
import com.example.restaurantbackendapplication1.service.ItemService;
import com.example.restaurantbackendapplication1.service.MenuItemService;
import com.example.restaurantbackendapplication1.service.MenuSectionService;
import com.example.restaurantbackendapplication1.service.MenuService;
import com.example.restaurantbackendapplication1.service.RecipeItemService;
import com.example.restaurantbackendapplication1.service.RecipeService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/sections/{section-id}/items/{item-id}/recipes/{recipe-id}/recipe-items")
public class RecipeItemController {

    private final RecipeItemService recipeItemService;
    private final RecipeService recipeService;
    private final MenuItemService menuItemService;
    private final MenuSectionService menuSectionService;
    private final MenuService menuService;
    private final ItemService itemService;

    public RecipeItemController(RecipeItemService recipeItemService,
                                RecipeService recipeService,
                                MenuItemService menuItemService,
                                MenuSectionService menuSectionService,
                                MenuService menuService,
                                ItemService itemService) {
        this.recipeItemService = recipeItemService;
        this.recipeService = recipeService;
        this.menuItemService = menuItemService;
        this.menuSectionService = menuSectionService;
        this.menuService = menuService;
        this.itemService = itemService;
    }

    private RecipeEntity resolveRecipe(Long menuId, Long sectionId, Long itemId, Long recipeId) {
        MenuEntity menuEntity = menuService.getEntityById(menuId);
        MenuSectionEntity menuSectionEntity = menuSectionService.getEntityById(sectionId, menuEntity);
        MenuItemEntity menuItemEntity = menuItemService.getEntityById(itemId, menuSectionEntity);
        return recipeService.getEntityById(recipeId, menuItemEntity);
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable("item-id") Long itemId,
            @PathVariable("recipe-id") Long recipeId,
            @Valid @RequestBody CreateRecipeItemRequest request) {
        RecipeEntity recipeEntity = resolveRecipe(menuId, sectionId, itemId, recipeId);
        ItemEntity itemEntity = itemService.getEntityById(request.getItemId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recipeItemService.create(recipeEntity, itemEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable("item-id") Long itemId,
            @PathVariable("recipe-id") Long recipeId,
            @PathVariable Long id) {
        RecipeEntity recipeEntity = resolveRecipe(menuId, sectionId, itemId, recipeId);
        return ResponseEntity.ok(recipeItemService.getById(id, recipeEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable("item-id") Long itemId,
            @PathVariable("recipe-id") Long recipeId,
            @Valid @ParameterObject PaginatedRequest request) {
        RecipeEntity recipeEntity = resolveRecipe(menuId, sectionId, itemId, recipeId);
        return ResponseEntity.ok(recipeItemService.getAll(recipeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable("item-id") Long itemId,
            @PathVariable("recipe-id") Long recipeId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateRecipeItemRequest request) {
        RecipeEntity recipeEntity = resolveRecipe(menuId, sectionId, itemId, recipeId);
        RecipeItemEntity entity = recipeItemService.getEntityById(id, recipeEntity);
        ItemEntity itemEntity = itemService.getEntityById(request.getItemId());
        return ResponseEntity.ok(recipeItemService.update(entity, itemEntity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("section-id") Long sectionId,
            @PathVariable("item-id") Long itemId,
            @PathVariable("recipe-id") Long recipeId,
            @PathVariable Long id) {
        RecipeEntity recipeEntity = resolveRecipe(menuId, sectionId, itemId, recipeId);
        RecipeItemEntity entity = recipeItemService.getEntityById(id, recipeEntity);
        return ResponseEntity.ok(recipeItemService.delete(entity));
    }
}
