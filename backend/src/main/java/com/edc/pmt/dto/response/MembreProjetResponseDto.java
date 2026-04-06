package com.edc.pmt.dto.response;

import java.time.LocalDateTime;

import com.edc.pmt.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembreProjetResponseDto {
    private Long id;
    private UtilisateurResponseDto utilisateur;
    private ProjetResponseDto projet;
    private Role role;
    private LocalDateTime dateDeCreation;
}
