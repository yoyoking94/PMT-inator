package com.edc.pmt.dto.request;

import com.edc.pmt.enums.Role;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembreProjetRequestDto {
    @NotNull(message = "L'email de l'utilisateur est obligatoire")
    private String email;

    @NotNull(message = "Le rôle est obligatoire")
    private Role role;
}
