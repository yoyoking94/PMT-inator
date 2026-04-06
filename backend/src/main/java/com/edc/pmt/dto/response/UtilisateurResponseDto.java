package com.edc.pmt.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtilisateurResponseDto
 {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime dateDeCreation;
}
