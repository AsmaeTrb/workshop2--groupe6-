package com.example.securityservice.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)   // important: username doit être unique
    private String username;
    @Column(nullable = false)
    private String password;
    @Column( nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Roles getRole() {
        return role;
    }

}

