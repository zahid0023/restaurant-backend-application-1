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
public class RestaurantImageDto {
    private Long id;
    private Long configId;
    private String externalId;
    private String url;
    private String caption;
    private Integer sortOrder;
}
