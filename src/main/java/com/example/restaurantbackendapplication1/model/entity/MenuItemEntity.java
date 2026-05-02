package com.example.restaurantbackendapplication1.model.entity;

import com.example.restaurantbackendapplication1.commons.model.entity.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "menu_items")
public class MenuItemEntity extends AuditableEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "menu_section_id", nullable = false)
    private MenuSectionEntity menuSectionEntity;

    @Size(max = 50)
    @NotNull
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @NotNull
    @Column(name = "price", nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = false;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured = false;

    @Column(name = "is_veg")
    private Boolean isVeg;

    @OneToMany(mappedBy = "menuItemEntity", cascade = CascadeType.ALL)
    private Set<MenuItemLocaleEntity> menuItemLocaleEntities = new LinkedHashSet<>();

    @OneToOne(mappedBy = "menuItemEntity", fetch = FetchType.LAZY)
    private RecipeEntity recipeEntity;
}