package com.example.restaurantbackendapplication1.dish.dto.request;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DishFilterRequest extends PaginatedRequest {
    private Boolean isFeatured;
}
