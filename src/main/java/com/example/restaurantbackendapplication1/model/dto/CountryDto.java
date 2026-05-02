package com.example.restaurantbackendapplication1.model.dto;

import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CountryDto {
    private Long id;
    private String code;

    private String iso3Code;
    private String phoneCode;
    private Integer sortOrder;
}
