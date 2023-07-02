package com.felipefaria.reservationapi.resource.repository.jpa.entities;

import com.felipefaria.reservationapi.domain.entities.PropertyDomain;
import jakarta.persistence.*;
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
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private  String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Property(PropertyDomain propertyDomain) {
        this.id = propertyDomain.getId();
        this.name = propertyDomain.getName();
        this.owner = new User(propertyDomain.getOwner());
    }
}
