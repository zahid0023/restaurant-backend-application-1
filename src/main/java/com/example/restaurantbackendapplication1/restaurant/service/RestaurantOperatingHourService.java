package com.example.restaurantbackendapplication1.restaurant.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.restaurant.dto.request.schedule.ScheduleRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.response.RestaurantScheduleResponse;

public interface RestaurantOperatingHourService {

    RestaurantScheduleResponse getSchedule();

    SuccessResponse createSchedule(ScheduleRequest request);

    SuccessResponse updateSchedule(ScheduleRequest request);
}
