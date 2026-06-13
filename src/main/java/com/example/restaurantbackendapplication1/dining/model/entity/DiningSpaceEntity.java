package com.example.restaurantbackendapplication1.dining.model.entity;

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
@Table(name = "dining_spaces")
public class DiningSpaceEntity extends AuditableEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dining_space_type_id", nullable = false)
    private DiningSpaceTypeEntity diningSpaceTypeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id")
    private FloorEntity floorEntity;

    @Size(max = 50)
    @NotNull
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_bookable", nullable = false)
    private Boolean isBookable = false;

    @OneToMany(mappedBy = "diningSpaceEntity", cascade = CascadeType.ALL)
    private Set<DiningSpaceLocaleEntity> diningSpaceLocaleEntities = new LinkedHashSet<>();
}