package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface ItemSummary {
    Long getId();

    String getCode();

    @Value("#{target.unitTypeEntity}")
    UnitTypeSummary getUnitType();

    Integer getSortOrder();

    @Value("#{target.itemLocaleEntities}")
    List<LocaleSummary> getLocales();

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    interface LocaleSummary {
        Long getId();

        @Value("#{target.localeEntity.code}")
        String getLocaleCode();

        String getName();
        String getDescription();
        Integer getSortOrder();
    }
}
