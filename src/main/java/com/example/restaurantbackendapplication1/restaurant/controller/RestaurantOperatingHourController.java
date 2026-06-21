package com.example.restaurantbackendapplication1.restaurant.controller;

import com.example.restaurantbackendapplication1.restaurant.dto.request.schedule.ScheduleRequest;
import com.example.restaurantbackendapplication1.restaurant.service.RestaurantOperatingHourService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurant-operating-hours")
public class RestaurantOperatingHourController {

    private final RestaurantOperatingHourService restaurantOperatingHourService;

    public RestaurantOperatingHourController(RestaurantOperatingHourService restaurantOperatingHourService) {
        this.restaurantOperatingHourService = restaurantOperatingHourService;
    }

    @GetMapping("/schedule")
    public ResponseEntity<?> getSchedule() {
        return ResponseEntity.ok(restaurantOperatingHourService.getSchedule());
    }

    @GetMapping("/public/schedule")
    public ResponseEntity<?> getSchedulePublic() {
        return ResponseEntity.ok(restaurantOperatingHourService.getSchedule());
    }

    @PostMapping("/schedule")
    public ResponseEntity<?> createSchedule(@Valid @RequestBody ScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurantOperatingHourService.createSchedule(request));
    }

    @PutMapping("/schedule")
    public ResponseEntity<?> updateSchedule(@Valid @RequestBody ScheduleRequest request) {
        return ResponseEntity.ok(restaurantOperatingHourService.updateSchedule(request));
    }
}
