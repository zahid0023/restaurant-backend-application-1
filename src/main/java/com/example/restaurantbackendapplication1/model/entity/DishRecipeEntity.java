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
@Table(name = "dish_recipes")
public class DishRecipeEntity extends AuditableEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "dish_variant_id", nullable = false)
    private DishVariantEntity dishVariantEntity;

    @Size(max = 50)
    @Column(name = "code", length = 50)
    private String code;

    @OneToMany(mappedBy = "dishRecipeEntity", cascade = CascadeType.ALL)
    private Set<DishRecipeIngredientEntity> dishRecipeIngredientEntities = new LinkedHashSet<>();
}