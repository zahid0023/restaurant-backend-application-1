package com.example.restaurantbackendapplication1.menu.model.entity;

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
@Table(name = "menu_types")
public class MenuTypeEntity extends AuditableEntity {

    @Size(max = 50)
    @NotNull
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @OneToMany(mappedBy = "menuTypeEntity", cascade = CascadeType.ALL)
    private Set<MenuTypeLocaleEntity> menuTypeLocaleEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "menuTypeEntity")
    private Set<MenuCategoryEntity> menuCategoryEntities = new LinkedHashSet<>();

}