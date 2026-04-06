package com.edc.pmt.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetResponseDto {
    private Long id;
    private String nom;
    private String description;
    private LocalDate dateDeDebut;
    private LocalDateTime dateDeCreation;
    private UtilisateurResponseDto administrateur;
}
