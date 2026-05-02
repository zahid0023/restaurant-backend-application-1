package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface UnitSummary {
    Long getId();

    @Value("#{target.unitTypeEntity.id}")
    Long getUnitTypeId();

    String getCode();
    Boolean getIsBase();
    Integer getSortOrder();
}
