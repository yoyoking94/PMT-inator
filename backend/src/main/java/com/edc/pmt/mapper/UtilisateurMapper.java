package com.edc.pmt.mapper;

import org.springframework.stereotype.Component;
import com.edc.pmt.dto.request.UtilisateurRequestDto;
import com.edc.pmt.dto.response.UtilisateurResponseDto;
import com.edc.pmt.entity.Utilisateur;

@Component
public class UtilisateurMapper {
    public Utilisateur convertirRequeteDtoEnUtilisateur(UtilisateurRequestDto utilisateurRequestDto) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername(utilisateurRequestDto.getUsername());
        utilisateur.setEmail(utilisateurRequestDto.getEmail());
        utilisateur.setPassword(utilisateurRequestDto.getPassword());
        return utilisateur;
    }

    public UtilisateurResponseDto convertirUtilisateurEnResponseDto(Utilisateur utilisateur) {
        UtilisateurResponseDto utilisateurReponseDto = new UtilisateurResponseDto();
        utilisateurReponseDto.setId(utilisateur.getId());
        utilisateurReponseDto.setUsername(utilisateur.getUsername());
        utilisateurReponseDto.setEmail(utilisateur.getEmail());
        utilisateurReponseDto.setDateDeCreation(utilisateur.getDateDeCreation());
        return utilisateurReponseDto;
    }
}
