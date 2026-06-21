package com.example.restaurantbackendapplication1.restaurant.model.mapper;

import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantoperatinghour.CreateRestaurantOperatingHourRequest;
import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantOperatingHourDto;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantOperatingHourEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestaurantOperatingHourMapper {

    public RestaurantOperatingHourEntity create(CreateRestaurantOperatingHourRequest request) {
        RestaurantOperatingHourEntity entity = new RestaurantOperatingHourEntity();
        entity.setDayOfWeek(request.getDayOfWeek());
        entity.setSequenceNo(request.getSequenceNo());
        entity.setOpenTime(request.getOpenTime());
        entity.setCloseTime(request.getCloseTime());
        entity.setLabel(request.getLabel());
        entity.setClosesNextDay(Boolean.TRUE.equals(request.getClosesNextDay()));
        entity.setIsClosed(Boolean.TRUE.equals(request.getIsClosed()));
        return entity;
    }

    public RestaurantOperatingHourDto toDto(RestaurantOperatingHourEntity entity) {
        return RestaurantOperatingHourDto.builder()
                .id(entity.getId())
                .dayOfWeek(entity.getDayOfWeek())
                .sequenceNo(entity.getSequenceNo())
                .openTime(entity.getOpenTime())
                .closeTime(entity.getCloseTime())
                .label(entity.getLabel())
                .closesNextDay(entity.getClosesNextDay())
                .isClosed(entity.getIsClosed())
                .build();
    }
}
