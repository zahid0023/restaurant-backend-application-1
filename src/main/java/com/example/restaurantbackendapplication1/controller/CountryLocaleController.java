package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.countrylocale.CreateCountryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.countrylocale.UpdateCountryLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.model.entity.CountryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.CountryLocaleService;
import com.example.restaurantbackendapplication1.service.CountryService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/countries/{country-id}/locales")
public class CountryLocaleController {

    private final CountryLocaleService countryLocaleService;
    private final CountryService countryService;
    private final LocaleService localeService;

    public CountryLocaleController(
            CountryLocaleService countryLocaleService,
            CountryService countryService,
            LocaleService localeService) {
        this.countryLocaleService = countryLocaleService;
        this.countryService = countryService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("country-id") Long countryId,
            @Valid @RequestBody CreateCountryLocaleRequest request) {
        CountryEntity countryEntity = countryService.getEntityById(countryId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(countryLocaleService.create(countryEntity, localeEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("country-id") Long countryId,
            @PathVariable Long id) {
        CountryEntity countryEntity = countryService.getEntityById(countryId);
        return ResponseEntity.ok(countryLocaleService.getById(id, countryEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("country-id") Long countryId,
            @Valid @ParameterObject PaginatedRequest request) {
        CountryEntity countryEntity = countryService.getEntityById(countryId);
        return ResponseEntity.ok(countryLocaleService.getAll(countryEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("country-id") Long countryId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateCountryLocaleRequest request) {
        CountryEntity countryEntity = countryService.getEntityById(countryId);
        CountryLocaleEntity entity = countryLocaleService.getEntityById(id, countryEntity);
        LocaleEntity locale = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.ok(countryLocaleService.update(entity, locale, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("country-id") Long countryId,
            @PathVariable Long id) {
        CountryEntity countryEntity = countryService.getEntityById(countryId);
        CountryLocaleEntity entity = countryLocaleService.getEntityById(id, countryEntity);
        return ResponseEntity.ok(countryLocaleService.delete(entity));
    }
}
