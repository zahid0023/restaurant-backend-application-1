package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.dto.request.itemcategory.AssignItemRequest;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemItemCategoryEntity;
import com.example.restaurantbackendapplication1.service.ItemCategoryService;
import com.example.restaurantbackendapplication1.service.ItemItemCategoryService;
import com.example.restaurantbackendapplication1.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item-item-categories")
public class ItemItemCategoryController {

    private final ItemItemCategoryService itemItemCategoryService;
    private final ItemService itemService;
    private final ItemCategoryService itemCategoryService;

    public ItemItemCategoryController(
            ItemItemCategoryService itemItemCategoryService,
            ItemService itemService,
            ItemCategoryService itemCategoryService) {
        this.itemItemCategoryService = itemItemCategoryService;
        this.itemService = itemService;
        this.itemCategoryService = itemCategoryService;
    }

    @PostMapping
    public ResponseEntity<?> assign(
            @RequestBody AssignItemRequest request) {
        ItemCategoryEntity itemCategoryEntity = itemCategoryService.getEntityById(request.getItemCategoryId());
        ItemEntity itemEntity = itemService.getEntityById(request.getItemId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemItemCategoryService.assign(itemCategoryEntity, itemEntity));
    }

    @DeleteMapping("/{item-id}/{item-category-id}")
    public ResponseEntity<?> unassign(
            @PathVariable("item-category-id") Long categoryId,
            @PathVariable("item-id") Long itemId) {
        ItemCategoryEntity itemCategoryEntity = itemCategoryService.getEntityById(categoryId);
        ItemEntity itemEntity = itemService.getEntityById(itemId);
        ItemItemCategoryEntity itemItemCategoryEntity = itemItemCategoryService.getAssignment(itemCategoryEntity, itemEntity);
        return ResponseEntity.ok(itemItemCategoryService.unassign(itemItemCategoryEntity));
    }
}
