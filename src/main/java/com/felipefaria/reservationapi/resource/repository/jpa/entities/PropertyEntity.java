package com.felipefaria.reservationapi.resource.repository.jpa.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "property")
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private  String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    public PropertyEntity(com.felipefaria.reservationapi.domain.entities.Property property) {
        this.id = property.getId();
        this.name = property.getName();
        this.owner = new UserEntity(property.getOwner());
    }
}
