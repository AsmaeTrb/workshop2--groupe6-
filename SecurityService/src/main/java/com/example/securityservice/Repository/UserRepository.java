package com.example.securityservice.Repository;

import com.example.securityservice.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findByUsername(String username);
}
