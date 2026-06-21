package com.example.restaurantbackendapplication1.restaurant.model.entity;

import com.example.restaurantbackendapplication1.commons.model.entity.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "restaurant_operating_hours")
public class RestaurantOperatingHourEntity extends AuditableEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @NotNull
    @Column(name = "sequence_no", nullable = false)
    private Short sequenceNo;

    @NotNull
    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @NotNull
    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @Column(name = "label")
    private String label;

    @ColumnDefault("false")
    @Column(name = "closes_next_day", nullable = false)
    private Boolean closesNextDay = false;

    @ColumnDefault("false")
    @Column(name = "is_closed", nullable = false)
    private Boolean isClosed = false;
}