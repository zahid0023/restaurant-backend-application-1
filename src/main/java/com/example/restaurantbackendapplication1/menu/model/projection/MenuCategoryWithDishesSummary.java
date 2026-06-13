package com.example.restaurantbackendapplication1.menu.model.projection;

import com.example.restaurantbackendapplication1.dish.model.projection.DishSummary;
import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface MenuCategoryWithDishesSummary {

    Long getId();
    String getCode();
    Integer getSortOrder();

    @Value("#{target.menuCategoryLocaleEntities}")
    List<LocaleSummary> getLocales();

    @Value("#{target.menuCategoryDishEntities.![dishEntity]}")
    List<DishSummary> getDishes();

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
