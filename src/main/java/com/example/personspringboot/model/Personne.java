package com.example.personspringboot.model;

import javax.persistence.*;
import java.io.Serializable;


import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "personnes")
public class Personne implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Le prénom ne peut pas être vide.")
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotBlank(message = "Le nom ne peut pas être vide.")
    @Column(name = "nom", nullable = false)
    private String nom;

    public Personne() {}

    public Personne(String prenom, String nom) {

        this.prenom = prenom;
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }
}
