package com.example.restaurantbackendapplication1.item.model.entity;

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
@Table(name = "item_categories")
public class ItemCategoryEntity extends AuditableEntity {

    @Size(max = 50)
    @NotNull
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @OneToMany(mappedBy = "itemCategoryEntity", cascade = CascadeType.ALL)
    private Set<ItemCategoryLocaleEntity> itemCategoryLocaleEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "itemCategoryEntity", cascade = CascadeType.ALL)
    private Set<ItemItemCategoryEntity> itemItemCategoryEntities = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ItemCategoryEntity itemCategoryEntity;

    @OneToMany(mappedBy = "itemCategoryEntity", fetch = FetchType.LAZY)
    private Set<ItemCategoryEntity> subCategoryEntities = new LinkedHashSet<>();

}