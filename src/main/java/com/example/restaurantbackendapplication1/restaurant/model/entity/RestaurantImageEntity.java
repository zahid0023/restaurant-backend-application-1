package com.example.restaurantbackendapplication1.restaurant.model.entity;

import com.example.restaurantbackendapplication1.commons.model.entity.AuditableEntity;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "restaurant_images")
public class RestaurantImageEntity extends AuditableEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "config_id", nullable = false)
    private RestaurantImageHostingConfigEntity configEntity;

    @Size(max = 255)
    @Column(name = "external_id")
    private String externalId;

    @NotNull
    @Column(name = "url", nullable = false, length = Integer.MAX_VALUE)
    private String url;

    @Size(max = 255)
    @Column(name = "caption")
    private String caption;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;
}