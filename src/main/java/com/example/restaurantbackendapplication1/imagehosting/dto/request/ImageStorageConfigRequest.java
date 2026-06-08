package com.example.restaurantbackendapplication1.imagehosting.dto.request;

import com.example.restaurantbackendapplication1.imagehosting.enums.ImageHostingProvider;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class ImageStorageConfigRequest {
    @NotNull(message = "Provider must not be null")
    private ImageHostingProvider provider;

    @NotNull(message = "Config must not be null")
    private Map<String, String> config;
}
