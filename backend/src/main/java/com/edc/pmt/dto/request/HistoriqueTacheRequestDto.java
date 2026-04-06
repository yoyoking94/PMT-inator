package com.edc.pmt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoriqueTacheRequestDto {
    @NotNull(message = "L'identifiant de la tache est obligatoire")
    private Long tacheId;
    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire")
    private Long modifieParId;
    @NotBlank(message = "Le champ modifier est obligatoire")
    private String champModifie;
    @NotBlank(message = "L'ancienne valeur est obligatoire")
    private String ancienneValeur;
    @NotBlank(message = "La nouvelle valeur est obligatoire")
    private String nouvelleValeur;
}
