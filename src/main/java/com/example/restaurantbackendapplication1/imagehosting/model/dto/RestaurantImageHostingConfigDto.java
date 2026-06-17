package com.example.restaurantbackendapplication1.imagehosting.model.dto;

import com.example.restaurantbackendapplication1.imagehosting.enums.ImageHostingProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantImageHostingConfigDto {
    private Long id;
    private ImageHostingProvider provider;
    private Map<String, String> config;
}
