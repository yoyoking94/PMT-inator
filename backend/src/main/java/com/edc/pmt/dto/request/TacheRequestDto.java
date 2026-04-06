package com.edc.pmt.dto.request;

import java.time.LocalDate;

import com.edc.pmt.enums.Priorite;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TacheRequestDto {
    @NotBlank(message = "Le nom de la tache est obligatoire")
    private String nom;

    @NotBlank(message = "La description de la tache est obligatoire")
    private String description;

    @NotNull(message = "Une date d'échéance est obligatoire")
    private LocalDate dateEcheant;

    @NotNull(message = "La priorité de la tâche est obligatoire")
    private Priorite priorite;

    private Long modificateurId;
}
