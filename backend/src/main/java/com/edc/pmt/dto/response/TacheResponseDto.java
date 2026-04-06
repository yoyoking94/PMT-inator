package com.edc.pmt.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.edc.pmt.enums.Priorite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TacheResponseDto {
    private Long id;
    private String nom;
    private String description;
    private LocalDate dateEcheant;
    private Priorite priorite;
    private LocalDate dateDeFin;
    private LocalDateTime dateDeCreation;  
    private ProjetResponseDto projet;
    private UtilisateurResponseDto membreAssigne;
}
