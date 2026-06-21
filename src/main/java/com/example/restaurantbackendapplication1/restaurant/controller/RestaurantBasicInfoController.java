package com.example.restaurantbackendapplication1.restaurant.controller;

import com.example.restaurantbackendapplication1.address.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.address.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.address.service.CityService;
import com.example.restaurantbackendapplication1.address.service.CountryService;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageMetaRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.response.ImageUploadResponse;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import com.example.restaurantbackendapplication1.imagehosting.service.ImageUploadService;
import com.example.restaurantbackendapplication1.imagehosting.service.RestaurantImageHostingConfigService;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.UpdateRestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoEntity;
import com.example.restaurantbackendapplication1.restaurant.service.RestaurantBasicInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant-basic-info")
public class
RestaurantBasicInfoController {

    private final RestaurantBasicInfoService restaurantBasicInfoService;
    private final CountryService countryService;
    private final CityService cityService;
    private final RestaurantImageHostingConfigService imageHostingConfigService;
    private final ImageUploadService imageUploadService;

    public RestaurantBasicInfoController(RestaurantBasicInfoService restaurantBasicInfoService,
                                         CountryService countryService,
                                         CityService cityService,
                                         RestaurantImageHostingConfigService imageHostingConfigService,
                                         ImageUploadService imageUploadService) {
        this.restaurantBasicInfoService = restaurantBasicInfoService;
        this.countryService = countryService;
        this.cityService = cityService;
        this.imageHostingConfigService = imageHostingConfigService;
        this.imageUploadService = imageUploadService;
    }

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(restaurantBasicInfoService.get());
    }

    @GetMapping("/public")
    public ResponseEntity<?> getPublic() {
        return ResponseEntity.ok(restaurantBasicInfoService.get());
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UpdateRestaurantBasicInfoRequest request) {
        RestaurantBasicInfoEntity entity = restaurantBasicInfoService.getEntity();
        CountryEntity countryEntity = countryService.getEntityById(request.getCountryId());
        CityEntity cityEntity = cityService.getEntityById(request.getCountryId(), request.getCityId());
        return ResponseEntity.ok(restaurantBasicInfoService.update(entity, request, countryEntity, cityEntity));
    }

    @PostMapping("/logo")
    public ResponseEntity<?> uploadLogo(
            @RequestParam("config_id") Long configId,
            @RequestParam("file") MultipartFile file) {
        RestaurantBasicInfoEntity entity = restaurantBasicInfoService.getEntity();
        RestaurantImageHostingConfigEntity config = imageHostingConfigService.getEntityById(configId);
        ImageUploadResponse imageUploadResponse = imageUploadService.upload(file, config.getProvider(), config.getConfig());
        return ResponseEntity.ok(restaurantBasicInfoService.uploadLogo(entity, imageUploadResponse));
    }
}
