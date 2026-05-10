package com.example.restaurantbackendapplication1.dto.request.country;

import com.example.restaurantbackendapplication1.dto.request.countrylocale.CountryLocaleRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CountryRequest {

    @NotBlank
    @Size(max = 10)
    private String code;

    @Size(max = 10)
    private String iso3Code;

    @Size(max = 10)
    private String phoneCode;

    @NotNull
    private Integer sortOrder;

    private List<CountryLocaleRequest> locales;
}
