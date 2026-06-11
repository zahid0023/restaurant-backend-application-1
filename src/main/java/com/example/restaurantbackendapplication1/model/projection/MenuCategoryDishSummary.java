package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface MenuCategoryDishSummary {

    Long getId();

    @Value("#{target.dishEntity.id}")
    Long getDishId();

    @Value("#{target.dishEntity.code}")
    String getDishCode();

    @Value("#{target.dishEntity.sortOrder}")
    Integer getDishSortOrder();

    @Value("#{target.dishEntity.dishesLocaleEntities}")
    List<DishLocaleInfo> getLocales();

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    interface DishLocaleInfo {
        Long getId();

        @Value("#{target.localeEntity.id}")
        Long getLocaleId();

        String getName();
        String getDescription();
        Integer getSortOrder();
    }
}
