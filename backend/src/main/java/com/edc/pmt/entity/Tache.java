package com.edc.pmt.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.edc.pmt.enums.Priorite;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "taches", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "nom", "projet_id" })
})
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String description;

    private LocalDate dateEcheant;

    @Enumerated(EnumType.STRING)
    private Priorite priorite;

    private LocalDate dateDeFin;

    private LocalDateTime dateDeCreation = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "membre_assigne_id")
    private Utilisateur membreAssigne;

}
