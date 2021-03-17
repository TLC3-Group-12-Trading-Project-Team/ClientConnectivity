package com.TradeProject.ClientConnectivity.client;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;


@Getter
@Service
@AllArgsConstructor
@ToString
@Setter
@NoArgsConstructor
@Table
@Entity(name = "users")
public class Client {
    @Id
    @SequenceGenerator(
            name = "client_sequence",
            sequenceName = "client_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "client_sequence"
    )
    @Column(
            nullable = false
    )
    private Long id;
    private String email;
    @Column(
            nullable = false
    )
    private String password;
    @Column(
            nullable = false
    )
    private String firstname;
    @Column(
            nullable = false
    )
    private String lastname;

    public Client(String email, String password, String firstname, String lastname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}


