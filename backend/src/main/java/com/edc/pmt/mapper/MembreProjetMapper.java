package com.edc.pmt.mapper;

import org.springframework.stereotype.Component;

import com.edc.pmt.dto.request.MembreProjetRequestDto;
import com.edc.pmt.dto.response.MembreProjetResponseDto;
import com.edc.pmt.entity.MembreProjet;

@Component
public class MembreProjetMapper {
    private final ProjetMapper projetMapper;
    private final UtilisateurMapper utilisateurMapper;

    public MembreProjetMapper(ProjetMapper projetMapper, UtilisateurMapper utilisateurMapper) {
        this.projetMapper = projetMapper;
        this.utilisateurMapper = utilisateurMapper;
    }

    public MembreProjet convertirRequestDtoEnMembreProjet(MembreProjetRequestDto membreProjetRequestDto) {
        MembreProjet membreProjet = new MembreProjet();
        membreProjet.setRole(membreProjetRequestDto.getRole());
        return membreProjet;
    }

    public MembreProjetResponseDto convertirMembreProjetEnResponseDto(MembreProjet membreProjet) {
        MembreProjetResponseDto membreProjetResponseDto = new MembreProjetResponseDto();
        membreProjetResponseDto.setId(membreProjet.getId());
        membreProjetResponseDto.setRole(membreProjet.getRole());
        membreProjetResponseDto.setDateDeCreation(membreProjet.getDateDeCreation());
        membreProjetResponseDto.setUtilisateur(utilisateurMapper.convertirUtilisateurEnResponseDto(membreProjet.getUtilisateur()));
        membreProjetResponseDto.setProjet(projetMapper.convertirProjetEnResponseDto(membreProjet.getProjet()));
        return membreProjetResponseDto;
    }
}
