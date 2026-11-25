package com.payflowx.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

// "user" is a reserved word in PostgreSQL, so the table is called "users"
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Comma-separated role names without the ROLE_ prefix, e.g. "MERCHANT" or "MERCHANT,ADMIN"
    @Column(nullable = false)
    private String roles;
}
