package com.example.restaurantbackendapplication1.address.dto.response;

import com.example.restaurantbackendapplication1.address.model.dto.CountryLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CountryLocaleResponse {
    private final CountryLocaleDto countryLocale;

    public CountryLocaleResponse(CountryLocaleDto countryLocale) {
        this.countryLocale = countryLocale;
    }
}
