package com.edc.pmt.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoriqueTacheResponseDto {
    private Long id;
    private TacheResponseDto tache;
    private UtilisateurResponseDto modifiePar;
    private String champModifie;
    private String ancienneValeur;
    private String nouvelleValeur;
    private LocalDateTime dateDeModification;
}
