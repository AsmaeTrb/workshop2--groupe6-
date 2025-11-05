package com.example.fillierev1.DTO;

import com.example.fillierev1.Entities.Filliere;
import lombok.Data;

@Data
public class FilliereResponse {
    private Integer idfilliere ;
    private String code;
    private String libelle ;
    // --- Getters ---
    public Integer getIdfilliere() {
        return idfilliere;
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    // --- Setters ---
    public void setIdfilliere(Integer idfilliere) {
        this.idfilliere = idfilliere;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
