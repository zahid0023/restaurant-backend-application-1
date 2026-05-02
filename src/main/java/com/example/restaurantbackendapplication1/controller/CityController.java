package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.city.CreateCityRequest;
import com.example.restaurantbackendapplication1.dto.request.city.UpdateCityRequest;
import com.example.restaurantbackendapplication1.dto.request.citylocale.CityLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.CityService;
import com.example.restaurantbackendapplication1.service.CountryService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {

    private final CityService cityService;
    private final CountryService countryService;
    private final LocaleService localeService;

    public CityController(CityService cityService,
                          CountryService countryService,
                          LocaleService localeService) {
        this.cityService = cityService;
        this.countryService = countryService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateCityRequest request) {
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), CityLocaleRequest::getLocaleId, localeService);
        CountryEntity countryEntity = countryService.getEntityById(request.getCountryId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.create(request, countryEntity, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(cityService.getAll(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCityRequest request) {
        CityEntity entity = cityService.getEntityById(id);
        return ResponseEntity.ok(cityService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.delete(id));
    }
}
