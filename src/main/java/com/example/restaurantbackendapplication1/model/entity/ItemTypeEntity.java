package com.example.restaurantbackendapplication1.model.entity;

import com.example.restaurantbackendapplication1.commons.model.entity.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "item_types")
public class ItemTypeEntity extends AuditableEntity {

    @Size(max = 50)
    @NotNull
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @NotNull
    @Column(name = "is_consumable", nullable = false)
    private Boolean isConsumable = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @OneToMany(mappedBy = "itemTypeEntity", cascade = CascadeType.ALL)
    private Set<ItemTypeLocaleEntity> itemTypeLocaleEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "itemTypeEntity")
    private Set<ItemEntity> itemEntities = new LinkedHashSet<>();

}