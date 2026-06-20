package com.example.restaurantbackendapplication1.dish.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariant.CreateDishVariantRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariant.UpdateDishVariantRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantingredient.CreateDishVariantIngredientRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantlocale.CreateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dish.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageMetaRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageRequest;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import com.example.restaurantbackendapplication1.imagehosting.service.ImageUploadService;
import com.example.restaurantbackendapplication1.imagehosting.service.RestaurantImageHostingConfigService;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.dish.service.DishService;
import com.example.restaurantbackendapplication1.dish.service.DishVariantService;
import com.example.restaurantbackendapplication1.item.service.ItemService;
import com.example.restaurantbackendapplication1.locale.service.LocaleService;
import com.example.restaurantbackendapplication1.unit.service.UnitService;
import com.example.restaurantbackendapplication1.commons.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/v1/dishes/{dish-id}/variants")
public class DishVariantController {

    private final DishVariantService dishVariantService;
    private final DishService dishService;
    private final LocaleService localeService;
    private final ItemService itemService;
    private final UnitService unitService;
    private final ImageUploadService imageUploadService;
    private final RestaurantImageHostingConfigService restaurantImageHostingConfigService;

    public DishVariantController(DishVariantService dishVariantService,
                                 DishService dishService,
                                 LocaleService localeService,
                                 ItemService itemService,
                                 UnitService unitService,
                                 ImageUploadService imageUploadService,
                                 RestaurantImageHostingConfigService restaurantImageHostingConfigService) {
        this.dishVariantService = dishVariantService;
        this.dishService = dishService;
        this.localeService = localeService;
        this.itemService = itemService;
        this.unitService = unitService;
        this.imageUploadService = imageUploadService;
        this.restaurantImageHostingConfigService = restaurantImageHostingConfigService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @PathVariable("dish-id") Long dishId,
            @RequestParam(value = "config-id", required = false) Long configId,
            @RequestPart("data") CreateDishVariantRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestPart(value = "image-metas", required = false) List<ImageMetaRequest> imageMetaRequests) {

        boolean hasImages = images != null && !images.isEmpty();
        if (hasImages) {
            if (configId == null) {
                throw new ResponseStatusException(BAD_REQUEST, "config-id is required when images are provided");
            }
            if (imageMetaRequests == null || imageMetaRequests.isEmpty()) {
                throw new ResponseStatusException(BAD_REQUEST, "image-metas is required and must not be empty when images are provided");
            }
        }

        DishEntity dishEntity = dishService.getEntityById(dishId);

        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), CreateDishVariantLocaleRequest::getLocaleId, localeService);
        Map<Long, ItemEntity> itemEntityMap = LocaleUtils.resolveEntityMap(
                request.getIngredients(), CreateDishVariantIngredientRequest::getItemId,
                itemService::getAll, ItemEntity::getId);
        Map<Long, UnitEntity> unitEntityMap = LocaleUtils.resolveEntityMap(
                request.getIngredients(), CreateDishVariantIngredientRequest::getUnitId,
                unitService::getAll, UnitEntity::getId);

        RestaurantImageHostingConfigEntity configEntity = null;
        List<ImageRequest> imageRequests = null;
        if (hasImages) {
            configEntity = restaurantImageHostingConfigService.getEntityById(configId);
            imageRequests = imageUploadService.uploadAll(
                    images,
                    imageMetaRequests,
                    configEntity.getProvider(),
                    configEntity.getConfig()
            );
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishVariantService.create(dishEntity, request, localeEntityMap, itemEntityMap, unitEntityMap, configEntity, imageRequests));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id) {
        return ResponseEntity.ok(dishVariantService.getById(dishId, id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("dish-id") Long dishId,
            @Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(dishVariantService.getAll(dishId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishVariantRequest request) {
        DishVariantEntity entity = dishVariantService.getEntityById(dishId, id);
        return ResponseEntity.ok(dishVariantService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id) {
        DishVariantEntity entity = dishVariantService.getEntityById(dishId, id);
        return ResponseEntity.ok(dishVariantService.delete(entity));
    }
}
