package com.example.restaurantbackendapplication1.dto.request.city;

import com.example.restaurantbackendapplication1.dto.request.citylocale.CityLocaleRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CityRequest {

    @NotNull
    private Long countryId;

    @Size(max = 50)
    private String code;

    @NotNull
    private Integer sortOrder;

    private List<CityLocaleRequest> locales;
}
