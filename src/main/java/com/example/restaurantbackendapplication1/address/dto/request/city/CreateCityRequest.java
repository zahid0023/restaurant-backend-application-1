package com.example.restaurantbackendapplication1.address.dto.request.city;

import com.example.restaurantbackendapplication1.address.dto.request.citylocale.CreateCityLocaleRequest;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateCityRequest extends CityRequest {
    @Size(max = 50)
    private String code;
    private List<CreateCityLocaleRequest> locales;
}
