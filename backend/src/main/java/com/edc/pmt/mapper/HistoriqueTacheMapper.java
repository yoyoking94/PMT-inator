package com.edc.pmt.mapper;

import org.springframework.stereotype.Component;

import com.edc.pmt.dto.request.HistoriqueTacheRequestDto;
import com.edc.pmt.dto.response.HistoriqueTacheResponseDto;
import com.edc.pmt.entity.HistoriqueTache;
import com.edc.pmt.entity.Tache;
import com.edc.pmt.entity.Utilisateur;

@Component
public class HistoriqueTacheMapper {

    private final TacheMapper tacheMapper;
    private final UtilisateurMapper utilisateurMapper;

    public HistoriqueTacheMapper(TacheMapper tacheMapper,
            UtilisateurMapper utilisateurMapper) {
        this.tacheMapper = tacheMapper;
        this.utilisateurMapper = utilisateurMapper;
    }

    public HistoriqueTache convertirRequestDtoEnHistoriqueTache(HistoriqueTacheRequestDto historiqueTacheRequestDto) {
        HistoriqueTache historiqueTache = new HistoriqueTache();
        Tache tache = new Tache();
        tache.setId(historiqueTacheRequestDto.getTacheId());
        historiqueTache.setTache(tache);
        Utilisateur modifiePar = new Utilisateur();
        modifiePar.setId(historiqueTacheRequestDto.getModifieParId());
        historiqueTache.setModifiePar(modifiePar);
        historiqueTache.setChampModifie(historiqueTacheRequestDto.getChampModifie());
        historiqueTache.setAncienneValeur(historiqueTacheRequestDto.getAncienneValeur());
        historiqueTache.setNouvelleValeur(historiqueTacheRequestDto.getNouvelleValeur());
        return historiqueTache;
    }

    public HistoriqueTacheResponseDto convertirHistoriqueTacheEnResponseDto(HistoriqueTache historiqueTache) {
        HistoriqueTacheResponseDto historiqueTacheResponseDto = new HistoriqueTacheResponseDto();
        historiqueTacheResponseDto.setId(historiqueTache.getId());
        historiqueTacheResponseDto
                .setModifiePar(utilisateurMapper.convertirUtilisateurEnResponseDto(historiqueTache.getModifiePar()));
        historiqueTacheResponseDto.setChampModifie(historiqueTache.getChampModifie());
        historiqueTacheResponseDto.setAncienneValeur(historiqueTache.getAncienneValeur());
        historiqueTacheResponseDto.setNouvelleValeur(historiqueTache.getNouvelleValeur());
        historiqueTacheResponseDto.setDateDeModification(historiqueTache.getDateDeModification());
        historiqueTacheResponseDto.setTache(tacheMapper.convertirTacheEnResponseDto(historiqueTache.getTache()));
        return historiqueTacheResponseDto;
    }
}
