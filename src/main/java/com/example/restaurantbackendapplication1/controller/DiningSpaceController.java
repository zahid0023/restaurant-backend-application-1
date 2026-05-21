package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspace.CreateDiningSpaceRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspace.UpdateDiningSpaceRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.CreateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.DiningSpaceService;
import com.example.restaurantbackendapplication1.service.DiningSpaceTypeService;
import com.example.restaurantbackendapplication1.service.FloorService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/dining-spaces")
public class DiningSpaceController {

    private final DiningSpaceService diningSpaceService;
    private final DiningSpaceTypeService diningSpaceTypeService;
    private final FloorService floorService;
    private final LocaleService localeService;

    public DiningSpaceController(DiningSpaceService diningSpaceService,
                                 DiningSpaceTypeService diningSpaceTypeService,
                                 FloorService floorService,
                                 LocaleService localeService) {
        this.diningSpaceService = diningSpaceService;
        this.diningSpaceTypeService = diningSpaceTypeService;
        this.floorService = floorService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateDiningSpaceRequest request) {
        DiningSpaceTypeEntity diningSpaceTypeEntity = diningSpaceTypeService.getEntityById(request.getDiningSpaceTypeId());
        FloorEntity floorEntity = request.getFloorId() != null ? floorService.getEntityById(request.getFloorId()) : null;
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), CreateDiningSpaceLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(diningSpaceService.create(request, diningSpaceTypeEntity, floorEntity, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(diningSpaceService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(diningSpaceService.getAll(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDiningSpaceRequest request) {
        DiningSpaceEntity entity = diningSpaceService.getEntityById(id);
        return ResponseEntity.ok(diningSpaceService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(diningSpaceService.delete(id));
    }
}
