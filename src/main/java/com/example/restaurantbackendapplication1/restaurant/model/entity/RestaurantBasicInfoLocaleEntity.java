package com.example.restaurantbackendapplication1.restaurant.model.entity;

import com.example.restaurantbackendapplication1.commons.model.entity.AuditableEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "restaurant_basic_info_locales")
public class RestaurantBasicInfoLocaleEntity extends AuditableEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_basic_info_id", nullable = false)
    private RestaurantBasicInfoEntity restaurantBasicInfoEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "locale_id", nullable = false)
    private LocaleEntity localeEntity;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 1024)
    @Column(name = "short_description", length = 1024)
    private String shortDescription;

    @Column(name = "address", length = Integer.MAX_VALUE)
    private String address;
}