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
@Table(name = "items")
public class ItemEntity extends AuditableEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unitEntity;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @OneToMany(mappedBy = "itemEntity", cascade = CascadeType.ALL)
    private Set<ItemLocaleEntity> itemLocaleEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "itemEntity", cascade = CascadeType.ALL)
    private Set<ItemItemCategoryEntity> itemItemCategoryEntities = new LinkedHashSet<>();

    @Size(max = 20)
    @NotNull
    @Column(name = "code", nullable = false, length = 20)
    private String code;

}