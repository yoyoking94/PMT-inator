package com.edc.pmt.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetRequestDto {
    @NotBlank(message = "Le nom du projet est obligatoire")
    private String nom;

    @NotBlank(message = "La description du projet est obligatoire")
    private String description;
    
    @NotNull(message = "Une date de début du projet est obligatoire")
    private LocalDate dateDeDebut;
}
