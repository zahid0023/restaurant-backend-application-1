package com.example.restaurantbackendapplication1.restaurant.model.entity;

import com.example.restaurantbackendapplication1.address.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.address.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.commons.model.entity.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "restaurant_basic_info")
public class RestaurantBasicInfoEntity extends AuditableEntity {

    @Size(max = 50)
    @NotNull
    @Column(name = "code", nullable = false, length = 50, unique = true)
    private String code;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "country_id", nullable = false)
    private CountryEntity countryEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity cityEntity;

    @Size(max = 50)
    @Column(name = "phone", length = 50)
    private String phone;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 500)
    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @OneToMany(mappedBy = "restaurantBasicInfoEntity", cascade = CascadeType.ALL)
    private Set<RestaurantBasicInfoLocaleEntity> restaurantBasicInfoLocaleEntities = new LinkedHashSet<>();

    @NotNull
    @Column(name = "estd", nullable = false)
    private Short estd;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

}