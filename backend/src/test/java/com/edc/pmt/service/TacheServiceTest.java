package com.edc.pmt.service;

import com.edc.pmt.dto.request.TacheRequestDto;
import com.edc.pmt.dto.response.TacheResponseDto;
import com.edc.pmt.entity.MembreProjet;
import com.edc.pmt.entity.Projet;
import com.edc.pmt.entity.Tache;
import com.edc.pmt.entity.Utilisateur;
import com.edc.pmt.enums.Priorite;
import com.edc.pmt.enums.Role;
import com.edc.pmt.mapper.TacheMapper;
import com.edc.pmt.repository.MembreProjetRepository;
import com.edc.pmt.repository.ProjetRepository;
import com.edc.pmt.repository.TacheRepository;
import com.edc.pmt.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TacheServiceTest {

    @Mock
    private TacheRepository tacheRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private MembreProjetRepository membreProjetRepository;

    @Mock
    private ProjetRepository projetRepository;

    @Mock
    private TacheMapper tacheMapper;

    @Mock
    private NotificationEmailService notificationEmailService;

    @InjectMocks
    private TacheService tacheService;

    private Utilisateur creerUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setEmail("jean.dupont@email.com");
        return utilisateur;
    }

    private Projet creerProjet() {
        Projet projet = new Projet();
        projet.setId(1L);
        projet.setNom("Mon Projet");
        return projet;
    }

    private Tache creerTache() {
        Tache tache = new Tache();
        tache.setId(1L);
        tache.setNom("Ma Tâche");
        tache.setPriorite(Priorite.MOYENNE);
        tache.setMembreAssigne(creerUtilisateur());
        tache.setProjet(creerProjet());
        return tache;
    }

    private TacheResponseDto creerTacheResponseDto() {
        TacheResponseDto responseDto = new TacheResponseDto();
        responseDto.setId(1L);
        responseDto.setNom("Ma Tâche");
        responseDto.setPriorite(Priorite.MOYENNE);
        return responseDto;
    }

    @Test
    void creerUneTache_doitSauvegarderEtEnvoyerEmailEtRetournerLaTache() {
        TacheRequestDto requestDto = new TacheRequestDto();
        requestDto.setNom("Ma Tâche");
        requestDto.setDescription("Description");
        requestDto.setDateEcheant(LocalDate.now().plusDays(7));
        requestDto.setPriorite(Priorite.MOYENNE);

        MembreProjet membreProjet = new MembreProjet();
        membreProjet.setRole(Role.MEMBRE);

        when(membreProjetRepository.findByProjetIdAndUtilisateurId(1L, 1L))
                .thenReturn(Optional.of(membreProjet)); // ← ajout
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(creerUtilisateur()));
        when(projetRepository.findById(1L)).thenReturn(Optional.of(creerProjet()));
        when(tacheMapper.convertirRequestDtoEnTache(any(), any(), any())).thenReturn(creerTache());
        when(tacheRepository.save(any())).thenReturn(creerTache());
        when(tacheMapper.convertirTacheEnResponseDto(any())).thenReturn(creerTacheResponseDto());
        doNothing().when(notificationEmailService).envoyerEmailAssignation(any(), any(), any());

        TacheResponseDto resultat = tacheService.creerUneTache(requestDto, 1L, 1L, 1L);

        assertThat(resultat).isNotNull();
        assertThat(resultat.getNom()).isEqualTo("Ma Tâche");
        verify(notificationEmailService, times(1)).envoyerEmailAssignation(any(), any(), any());
    }

    @Test
    void creerUneTache_doitLancerUneExceptionSiMembreInexistant() {
        MembreProjet membreProjet = new MembreProjet();
        membreProjet.setRole(Role.MEMBRE);

        when(membreProjetRepository.findByProjetIdAndUtilisateurId(any(), any()))
                .thenReturn(Optional.of(membreProjet)); // ← ajout
        when(utilisateurRepository.findById(99L)).thenReturn(Optional.empty());

        TacheRequestDto requestDto = new TacheRequestDto();

        assertThatThrownBy(() -> tacheService.creerUneTache(requestDto, 1L, 99L, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Aucun utilisateur trouvé avec l'identifiant : 99");
    }

    @Test
    void creerUneTache_doitLancerUneExceptionSiProjetInexistant() {
        MembreProjet membreProjet = new MembreProjet();
        membreProjet.setRole(Role.MEMBRE);

        when(membreProjetRepository.findByProjetIdAndUtilisateurId(any(), any()))
                .thenReturn(Optional.of(membreProjet)); // ← ajout
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(creerUtilisateur()));
        when(projetRepository.findById(99L)).thenReturn(Optional.empty());

        TacheRequestDto requestDto = new TacheRequestDto();

        assertThatThrownBy(() -> tacheService.creerUneTache(requestDto, 99L, 1L, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Aucun projet trouvé avec l'identifiant : 99");
    }

    @Test
    void recupererLesTachesDeUnProjet_doitRetournerLaListe() {
        when(tacheRepository.findByProjetId(1L)).thenReturn(List.of(creerTache()));
        when(tacheMapper.convertirTacheEnResponseDto(any())).thenReturn(creerTacheResponseDto());

        List<TacheResponseDto> resultat = tacheService.recupererLesTachesDeUnProjet(1L);

        assertThat(resultat).hasSize(1);
    }

    @Test
    void recupererUneTache_doitRetournerLaTache() {
        when(tacheRepository.findById(1L)).thenReturn(Optional.of(creerTache()));
        when(tacheMapper.convertirTacheEnResponseDto(any())).thenReturn(creerTacheResponseDto());

        TacheResponseDto resultat = tacheService.recupererUneTache(1L);

        assertThat(resultat.getId()).isEqualTo(1L);
    }

    @Test
    void recupererUneTache_doitLancerUneExceptionSiInexistante() {
        when(tacheRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tacheService.recupererUneTache(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Aucune tâche trouvée avec l'identifiant : 99");
    }
}