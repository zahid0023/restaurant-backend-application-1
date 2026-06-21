package com.example.restaurantbackendapplication1.restaurant.model.entity;

import com.example.restaurantbackendapplication1.commons.model.entity.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;

@Getter
@Setter
@Entity
@Table(name = "restaurant_closed_days")
public class RestaurantClosedDayEntity extends AuditableEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, unique = true)
    private DayOfWeek dayOfWeek;

    @Column(name = "note")
    private String note;
}
