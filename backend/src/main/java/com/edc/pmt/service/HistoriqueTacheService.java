package com.edc.pmt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.edc.pmt.dto.request.HistoriqueTacheRequestDto;
import com.edc.pmt.dto.response.HistoriqueTacheResponseDto;
import com.edc.pmt.entity.HistoriqueTache;
import com.edc.pmt.entity.Tache;
import com.edc.pmt.entity.Utilisateur;
import com.edc.pmt.mapper.HistoriqueTacheMapper;
import com.edc.pmt.repository.HistoriqueTacheRepository;
import com.edc.pmt.repository.TacheRepository;
import com.edc.pmt.repository.UtilisateurRepository;

@Service
public class HistoriqueTacheService {

    private final HistoriqueTacheMapper historiqueTacheMapper;
    private final HistoriqueTacheRepository historiqueTacheRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final TacheRepository tacheRepository;

    public HistoriqueTacheService(HistoriqueTacheMapper historiqueTacheMapper,
            HistoriqueTacheRepository historiqueTacheRepository,
            UtilisateurRepository utilisateurRepository,
            TacheRepository tacheRepository) {
        this.historiqueTacheMapper = historiqueTacheMapper;
        this.historiqueTacheRepository = historiqueTacheRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.tacheRepository = tacheRepository;
    }

    public HistoriqueTacheResponseDto enregistreModification(Long tacheId,
            HistoriqueTacheRequestDto historiqueTacheRequestDto) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new RuntimeException(
                        "Aucune tache trouvée avec l'identifiant : " + tacheId));

        Utilisateur modifiePar = utilisateurRepository.findById(historiqueTacheRequestDto.getModifieParId())
                .orElseThrow(() -> new RuntimeException(
                        "Aucun utilisateur trouvé avec l'identifiant : "
                                + historiqueTacheRequestDto.getModifieParId()));

        HistoriqueTache historiqueTache = historiqueTacheMapper
                .convertirRequestDtoEnHistoriqueTache(historiqueTacheRequestDto);
        historiqueTache.setTache(tache);
        historiqueTache.setModifiePar(modifiePar);

        HistoriqueTache historiqueTacheSauvegarde = historiqueTacheRepository.save(historiqueTache);
        return historiqueTacheMapper.convertirHistoriqueTacheEnResponseDto(historiqueTacheSauvegarde);
    }

    public List<HistoriqueTacheResponseDto> recupererLesHistoriquesDeUneTache(Long tacheId) {
        List<HistoriqueTache> listeHistoriqueTache = historiqueTacheRepository
                .findByTacheIdOrderByDateDeModificationDesc(tacheId);
        return listeHistoriqueTache.stream()
                .map(historiqueTacheMapper::convertirHistoriqueTacheEnResponseDto)
                .collect(Collectors.toList());
    }
}
