package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface DishVariantSummary {
    Long getId();

    @Value("#{target.dishEntity.id}")
    Long getDishId();

    String getCode();
    Integer getSortOrder();
    BigDecimal getPrice();
    Boolean getIsDefault();
    Boolean getIsAvailable();
    Boolean getIsFeatured();

    @Value("#{target.dishVariantLocaleEntities}")
    List<LocaleSummary> getLocales();

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    interface LocaleSummary {
        Long getId();

        @Value("#{target.localeEntity.id}")
        Long getLocaleId();

        String getName();
        String getDescription();
        Integer getSortOrder();
    }
}
