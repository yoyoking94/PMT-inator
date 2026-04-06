package com.edc.pmt.service;

import com.edc.pmt.dto.request.HistoriqueTacheRequestDto;
import com.edc.pmt.dto.response.HistoriqueTacheResponseDto;
import com.edc.pmt.entity.HistoriqueTache;
import com.edc.pmt.entity.Tache;
import com.edc.pmt.entity.Utilisateur;
import com.edc.pmt.mapper.HistoriqueTacheMapper;
import com.edc.pmt.repository.HistoriqueTacheRepository;
import com.edc.pmt.repository.TacheRepository;
import com.edc.pmt.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HistoriqueTacheServiceTest {

    @Mock
    private HistoriqueTacheRepository historiqueTacheRepository;

    @Mock
    private TacheRepository tacheRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private HistoriqueTacheMapper historiqueTacheMapper;

    @InjectMocks
    private HistoriqueTacheService historiqueTacheService;

    private HistoriqueTache creerHistoriqueTache() {
        Tache tache = new Tache();
        tache.setId(1L);

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);

        HistoriqueTache historiqueTache = new HistoriqueTache();
        historiqueTache.setId(1L);
        historiqueTache.setTache(tache);
        historiqueTache.setModifiePar(utilisateur);
        historiqueTache.setChampModifie("statut");
        historiqueTache.setAncienneValeur("EN_COURS");
        historiqueTache.setNouvelleValeur("TERMINE");
        historiqueTache.setDateDeModification(LocalDateTime.now());
        return historiqueTache;
    }

    @Test
    void enregistreModification_doitSauvegarderEtRetournerLHistorique() {
        HistoriqueTacheRequestDto requestDto = new HistoriqueTacheRequestDto();
        requestDto.setTacheId(1L);
        requestDto.setModifieParId(1L);
        requestDto.setChampModifie("statut");
        requestDto.setAncienneValeur("EN_COURS");
        requestDto.setNouvelleValeur("TERMINE");

        HistoriqueTacheResponseDto responseDto = new HistoriqueTacheResponseDto();
        responseDto.setId(1L);
        responseDto.setChampModifie("statut");

        when(tacheRepository.findById(1L)).thenReturn(Optional.of(new Tache()));
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(new Utilisateur()));
        when(historiqueTacheMapper.convertirRequestDtoEnHistoriqueTache(any()))
                .thenReturn(creerHistoriqueTache());
        when(historiqueTacheRepository.save(any())).thenReturn(creerHistoriqueTache());
        when(historiqueTacheMapper.convertirHistoriqueTacheEnResponseDto(any())).thenReturn(responseDto);

        HistoriqueTacheResponseDto resultat = historiqueTacheService.enregistreModification(1L, requestDto);

        assertThat(resultat).isNotNull();
        assertThat(resultat.getChampModifie()).isEqualTo("statut");
    }

    @Test
    void enregistreModification_doitLancerUneExceptionSiTacheInexistante() {
        when(tacheRepository.findById(99L)).thenReturn(Optional.empty());

        HistoriqueTacheRequestDto requestDto = new HistoriqueTacheRequestDto();

        assertThatThrownBy(() -> historiqueTacheService.enregistreModification(99L, requestDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Aucune tache trouvée avec l'identifiant : 99");
    }

    @Test
    void recupererLesHistoriquesDeUneTache_doitRetournerLaListe() {
        when(historiqueTacheRepository.findByTacheIdOrderByDateDeModificationDesc(1L))
                .thenReturn(List.of(creerHistoriqueTache()));
        when(historiqueTacheMapper.convertirHistoriqueTacheEnResponseDto(any()))
                .thenReturn(new HistoriqueTacheResponseDto());

        List<HistoriqueTacheResponseDto> resultat = historiqueTacheService.recupererLesHistoriquesDeUneTache(1L);

        assertThat(resultat).hasSize(1);
    }
}