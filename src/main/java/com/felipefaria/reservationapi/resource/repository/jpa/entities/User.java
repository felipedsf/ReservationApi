package com.felipefaria.reservationapi.resource.repository.jpa.entities;

import com.felipefaria.reservationapi.domain.entities.UserDomain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(max = 30)
    @Column(name = "last_name")
    private String lastName;

    public User(UserDomain user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
