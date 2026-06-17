package com.example.restaurantbackendapplication1.restaurant.controller;

import com.example.restaurantbackendapplication1.address.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.address.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.address.service.CityService;
import com.example.restaurantbackendapplication1.address.service.CountryService;
import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.locale.service.LocaleService;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.CreateRestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.UpdateRestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale.CreateRestaurantBasicInfoLocaleRequest;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoEntity;
import com.example.restaurantbackendapplication1.restaurant.service.RestaurantBasicInfoService;
import com.example.restaurantbackendapplication1.commons.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/restaurant-basic-info")
public class RestaurantBasicInfoController {

    private final RestaurantBasicInfoService restaurantBasicInfoService;
    private final CountryService countryService;
    private final CityService cityService;
    private final LocaleService localeService;

    public RestaurantBasicInfoController(RestaurantBasicInfoService restaurantBasicInfoService,
                                         CountryService countryService,
                                         CityService cityService,
                                         LocaleService localeService) {
        this.restaurantBasicInfoService = restaurantBasicInfoService;
        this.countryService = countryService;
        this.cityService = cityService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateRestaurantBasicInfoRequest request) {
        CountryEntity countryEntity = countryService.getEntityById(request.getCountryId());
        CityEntity cityEntity = cityService.getEntityById(request.getCountryId(), request.getCityId());
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), CreateRestaurantBasicInfoLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurantBasicInfoService.create(request, countryEntity, cityEntity, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantBasicInfoService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(restaurantBasicInfoService.getAll(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRestaurantBasicInfoRequest request) {
        RestaurantBasicInfoEntity entity = restaurantBasicInfoService.getEntityById(id);
        return ResponseEntity.ok(restaurantBasicInfoService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantBasicInfoService.delete(id));
    }
}
