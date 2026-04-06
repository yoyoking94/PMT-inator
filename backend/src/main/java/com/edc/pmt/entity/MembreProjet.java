package com.edc.pmt.entity;

import java.time.LocalDateTime;

import com.edc.pmt.enums.Role;

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
@Table(name = "membre_projet", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "utilisateur_id", "projet_id" })
})
public class MembreProjet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime dateDeCreation = LocalDateTime.now();

}
