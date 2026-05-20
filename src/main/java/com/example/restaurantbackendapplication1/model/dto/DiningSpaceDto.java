package com.example.restaurantbackendapplication1.model.dto;

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
public class DiningSpaceDto {
    private Long id;
    private Long diningSpaceTypeId;
    private Long floorId;
    private String code;
    private Integer sortOrder;
    private Integer capacity;
    private Boolean isBookable;
}
