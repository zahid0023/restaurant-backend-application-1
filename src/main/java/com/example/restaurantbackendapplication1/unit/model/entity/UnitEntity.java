package com.example.restaurantbackendapplication1.unit.model.entity;

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
@Table(name = "units")
public class UnitEntity extends AuditableEntity {

    @Size(max = 20)
    @NotNull
    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_type_id", nullable = false)
    private UnitTypeEntity unitTypeEntity;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_base", nullable = false)
    private Boolean isBase = false;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @OneToMany(mappedBy = "unitEntity", cascade = CascadeType.ALL)
    private Set<UnitLocaleEntity> unitLocaleEntities = new LinkedHashSet<>();

}