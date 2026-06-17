package com.example.restaurantbackendapplication1.restaurant.model.projection;

import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface RestaurantBasicInfoSummary {
    Long getId();
    String getCode();
    Integer getSortOrder();

    @Value("#{target.countryEntity.id}")
    Long getCountryId();

    @Value("#{target.cityEntity.id}")
    Long getCityId();

    String getPhone();
    String getEmail();
    String getLogoUrl();

    @Value("#{target.restaurantBasicInfoLocaleEntities}")
    List<LocaleSummary> getLocales();

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    interface LocaleSummary {
        Long getId();

        @Value("#{target.localeEntity.id}")
        Long getLocaleId();

        Integer getSortOrder();
        String getName();
        Short getEstd();
        String getShortDescription();
        Double getLat();
        Double getLon();
        String getAddress();
    }
}
