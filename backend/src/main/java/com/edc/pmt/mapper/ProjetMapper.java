package com.edc.pmt.mapper;

import org.springframework.stereotype.Component;

import com.edc.pmt.dto.request.ProjetRequestDto;
import com.edc.pmt.dto.response.ProjetResponseDto;
import com.edc.pmt.entity.Projet;
import com.edc.pmt.entity.Utilisateur;

@Component
public class ProjetMapper {

    private final UtilisateurMapper utilisateurMapper;

    public ProjetMapper(UtilisateurMapper utilisateurMapper) {
        this.utilisateurMapper = utilisateurMapper;
    }

    public Projet convertirRequestDtoEnProjet(ProjetRequestDto projetRequestDto, Utilisateur administrateur) {
        Projet projet = new Projet();
        projet.setNom(projetRequestDto.getNom());
        projet.setDescription(projetRequestDto.getDescription());
        projet.setDateDeDebut(projetRequestDto.getDateDeDebut());
        projet.setAdministrateur(administrateur);
        return projet;
    }

    public ProjetResponseDto convertirProjetEnResponseDto(Projet projet) {
        ProjetResponseDto projetResponseDto = new ProjetResponseDto();
        projetResponseDto.setId(projet.getId());
        projetResponseDto.setNom(projet.getNom());
        projetResponseDto.setDescription(projet.getDescription());
        projetResponseDto.setDateDeDebut(projet.getDateDeDebut());
        projetResponseDto.setDateDeCreation(projet.getDateDeCreation());
        projetResponseDto.setAdministrateur(utilisateurMapper.convertirUtilisateurEnResponseDto(projet.getAdministrateur()));
        return projetResponseDto;
    }
}
