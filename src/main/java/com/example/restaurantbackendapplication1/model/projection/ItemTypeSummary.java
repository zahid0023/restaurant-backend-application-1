package com.example.restaurantbackendapplication1.model.projection;

public interface ItemTypeSummary {
    Long getId();
    String getCode();
    Boolean getIsConsumable();
    Integer getSortOrder();
}
