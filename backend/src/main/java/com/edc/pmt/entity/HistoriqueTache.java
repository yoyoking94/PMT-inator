package com.edc.pmt.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
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
@Table(name = "historique_tache", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "champ_modifie", "ancienne_valeur", "nouvelle_valeur", "taches_id" })
})
public class HistoriqueTache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "taches_id")
    private Tache tache;

    @ManyToOne
    @JoinColumn(name = "modifie_par")
    private Utilisateur modifiePar;

    private String champModifie;

    private String ancienneValeur;

    private String nouvelleValeur;

    private LocalDateTime dateDeModification = LocalDateTime.now();
}
