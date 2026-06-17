package com.example.restaurantbackendapplication1.restaurant.model.dto;

import com.example.restaurantbackendapplication1.address.model.dto.CityDto;
import com.example.restaurantbackendapplication1.address.model.dto.CountryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantBasicInfoDto {
    private Long id;
    private String code;
    private Integer sortOrder;
    private Short estd;
    private Double lat;
    private Double lon;
    private CountryDto country;
    private CityDto city;
    private String phone;
    private String email;
    private String logoUrl;
    private List<RestaurantBasicInfoLocaleDto> locales;
}
