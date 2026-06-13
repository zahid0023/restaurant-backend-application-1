package com.example.restaurantbackendapplication1.inventory.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.inventory.dto.request.inventorytransaction.CreateInventoryTransactionRequest;
import com.example.restaurantbackendapplication1.inventory.dto.request.inventorytransaction.UpdateInventoryTransactionRequest;
import com.example.restaurantbackendapplication1.inventory.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.inventory.model.entity.InventoryTransactionEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.inventory.service.InventoryLocationService;
import com.example.restaurantbackendapplication1.inventory.service.InventoryTransactionService;
import com.example.restaurantbackendapplication1.item.service.ItemService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory-transactions")
public class InventoryTransactionController {

    private final InventoryTransactionService inventoryTransactionService;
    private final ItemService itemService;
    private final InventoryLocationService inventoryLocationService;

    public InventoryTransactionController(InventoryTransactionService inventoryTransactionService,
                                          ItemService itemService,
                                          InventoryLocationService inventoryLocationService) {
        this.inventoryTransactionService = inventoryTransactionService;
        this.itemService = itemService;
        this.inventoryLocationService = inventoryLocationService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateInventoryTransactionRequest request) {
        ItemEntity itemEntity = itemService.getEntityById(request.getItemId());
        InventoryLocationEntity inventoryLocationEntity = inventoryLocationService.getEntityById(request.getLocationId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryTransactionService.create(request, itemEntity, inventoryLocationEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryTransactionService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(inventoryTransactionService.getAll(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateInventoryTransactionRequest request) {
        InventoryTransactionEntity entity = inventoryTransactionService.getEntityById(id);
        ItemEntity itemEntity = itemService.getEntityById(request.getItemId());
        InventoryLocationEntity inventoryLocationEntity = inventoryLocationService.getEntityById(request.getLocationId());
        return ResponseEntity.ok(inventoryTransactionService.update(entity, request, itemEntity, inventoryLocationEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryTransactionService.delete(id));
    }
}
