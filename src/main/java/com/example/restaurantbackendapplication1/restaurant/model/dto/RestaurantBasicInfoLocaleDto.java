package com.example.restaurantbackendapplication1.restaurant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantBasicInfoLocaleDto {
    private Long id;
    private Long localeId;
    private Integer sortOrder;
    private String name;
    private Short estd;
    private String shortDescription;
    private Double lat;
    private Double lon;
    private String address;
}
