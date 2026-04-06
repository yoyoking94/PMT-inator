package com.edc.pmt.mapper;

import org.springframework.stereotype.Component;

import com.edc.pmt.dto.request.TacheRequestDto;
import com.edc.pmt.dto.response.TacheResponseDto;
import com.edc.pmt.entity.Projet;
import com.edc.pmt.entity.Tache;
import com.edc.pmt.entity.Utilisateur;

@Component
public class TacheMapper {

    private final ProjetMapper projetMapper;
    private final UtilisateurMapper utilisateurMapper;

    public TacheMapper(ProjetMapper projetMapper, UtilisateurMapper utilisateurMapper) {
        this.projetMapper = projetMapper;
        this.utilisateurMapper = utilisateurMapper;
    }

    public Tache convertirRequestDtoEnTache(TacheRequestDto tacheRequestDto, Projet projet, Utilisateur membreAssigne) {
        Tache tache = new Tache();
        tache.setNom(tacheRequestDto.getNom());
        tache.setDescription(tacheRequestDto.getDescription());
        tache.setDateEcheant(tacheRequestDto.getDateEcheant());
        tache.setPriorite(tacheRequestDto.getPriorite());
        tache.setProjet(projet);
        tache.setMembreAssigne(membreAssigne);
        return tache;
    }

    public TacheResponseDto convertirTacheEnResponseDto(Tache tache) {
        TacheResponseDto tacheResponseDto = new TacheResponseDto();
        tacheResponseDto.setId(tache.getId());
        tacheResponseDto.setNom(tache.getNom());
        tacheResponseDto.setDescription(tache.getDescription());
        tacheResponseDto.setDateEcheant(tache.getDateEcheant());
        tacheResponseDto.setPriorite(tache.getPriorite());
        tacheResponseDto.setDateDeFin(tache.getDateDeFin());
        tacheResponseDto.setDateDeCreation(tache.getDateDeCreation());
        tacheResponseDto.setProjet(projetMapper.convertirProjetEnResponseDto(tache.getProjet()));
        tacheResponseDto.setMembreAssigne(utilisateurMapper.convertirUtilisateurEnResponseDto(tache.getMembreAssigne()));
        return tacheResponseDto;
    }
}
