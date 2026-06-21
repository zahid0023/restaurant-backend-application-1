package com.example.restaurantbackendapplication1.restaurant.model.mapper;

import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantclosedday.CreateRestaurantClosedDayRequest;
import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantClosedDayDto;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantClosedDayEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestaurantClosedDayMapper {

    public RestaurantClosedDayEntity create(CreateRestaurantClosedDayRequest request) {
        RestaurantClosedDayEntity entity = new RestaurantClosedDayEntity();
        entity.setDayOfWeek(request.getDayOfWeek());
        entity.setNote(request.getNote());
        return entity;
    }

    public RestaurantClosedDayDto toDto(RestaurantClosedDayEntity entity) {
        return RestaurantClosedDayDto.builder()
                .id(entity.getId())
                .dayOfWeek(entity.getDayOfWeek())
                .note(entity.getNote())
                .build();
    }
}
