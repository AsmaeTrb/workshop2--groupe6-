package com.example.fillierev1.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Filliere {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idfilliere;
    @Column(unique = true, nullable = false)
    private String code;
    @Column(nullable = false)
     private String libelle;
}
