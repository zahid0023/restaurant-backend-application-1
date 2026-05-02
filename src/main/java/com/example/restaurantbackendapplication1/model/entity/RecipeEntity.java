package com.example.restaurantbackendapplication1.model.entity;

import com.example.restaurantbackendapplication1.commons.model.entity.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "recipes")
public class RecipeEntity extends AuditableEntity {

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "menu_item_id", nullable = false, unique = true)
    private MenuItemEntity menuItemEntity;

    @Size(max = 50)
    @Column(name = "code", length = 50)
    private String code;

    @OneToMany(mappedBy = "recipeEntity", cascade = CascadeType.ALL)
    private Set<RecipeItemEntity> recipeItemEntities = new LinkedHashSet<>();
}