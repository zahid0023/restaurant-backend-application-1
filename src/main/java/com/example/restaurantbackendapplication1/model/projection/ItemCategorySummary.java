package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface ItemCategorySummary {
    Long getId();

    @Value("#{target.itemTypeEntity.id}")
    Long getItemTypeId();

    @Value("#{target.itemCategoryEntity?.id}")
    Long getParentId();

    String getCode();
    Integer getSortOrder();

    @Value("#{target.itemCategoryLocaleEntities}")
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
