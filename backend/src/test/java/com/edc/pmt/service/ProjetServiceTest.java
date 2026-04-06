package com.edc.pmt.service;

import com.edc.pmt.dto.request.ProjetRequestDto;
import com.edc.pmt.dto.response.ProjetResponseDto;
import com.edc.pmt.entity.MembreProjet;
import com.edc.pmt.entity.Projet;
import com.edc.pmt.entity.Utilisateur;
import com.edc.pmt.mapper.ProjetMapper;
import com.edc.pmt.repository.MembreProjetRepository;
import com.edc.pmt.repository.ProjetRepository;
import com.edc.pmt.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjetServiceTest {

    @Mock
    private ProjetRepository projetRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private MembreProjetRepository membreProjetRepository;

    @Mock
    private ProjetMapper projetMapper;

    @InjectMocks
    private ProjetService projetService;

    private Utilisateur creerUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setUsername("Jean Dupont");
        utilisateur.setEmail("jean.dupont@email.com");
        return utilisateur;
    }

    private Projet creerProjet() {
        Projet projet = new Projet();
        projet.setId(1L);
        projet.setNom("Mon Projet");
        projet.setDescription("Description");
        projet.setDateDeDebut(LocalDate.now());
        projet.setDateDeCreation(LocalDateTime.now());
        projet.setAdministrateur(creerUtilisateur());
        return projet;
    }

    private ProjetResponseDto creerProjetResponseDto() {
        ProjetResponseDto responseDto = new ProjetResponseDto();
        responseDto.setId(1L);
        responseDto.setNom("Mon Projet");
        responseDto.setDescription("Description");
        return responseDto;
    }

    @Test
    void creerUnProjet_doitSauvegarderEtRetournerLeProjet() {
        ProjetRequestDto requestDto = new ProjetRequestDto();
        requestDto.setNom("Mon Projet");
        requestDto.setDescription("Description");
        requestDto.setDateDeDebut(LocalDate.now());

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(creerUtilisateur()));
        when(projetMapper.convertirRequestDtoEnProjet(any(), any())).thenReturn(creerProjet());
        when(projetRepository.save(any())).thenReturn(creerProjet());
        when(membreProjetRepository.save(any())).thenReturn(new MembreProjet());
        when(projetMapper.convertirProjetEnResponseDto(any())).thenReturn(creerProjetResponseDto());

        ProjetResponseDto resultat = projetService.creerUnProjet(requestDto, 1L);

        assertThat(resultat).isNotNull();
        assertThat(resultat.getNom()).isEqualTo("Mon Projet");
    }

    @Test
    void creerUnProjet_doitLancerUneExceptionSiAdministrateurInexistant() {
        when(utilisateurRepository.findById(99L)).thenReturn(Optional.empty());

        ProjetRequestDto requestDto = new ProjetRequestDto();
        requestDto.setNom("Mon Projet");
        requestDto.setDescription("Description");
        requestDto.setDateDeDebut(LocalDate.now());

        assertThatThrownBy(() -> projetService.creerUnProjet(requestDto, 99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Aucun utilisateur trouvé avec l'identifiant : 99");
    }

    @Test
    void recupererUnProjet_doitRetournerLeProjet() {
        when(projetRepository.findById(1L)).thenReturn(Optional.of(creerProjet()));
        when(projetMapper.convertirProjetEnResponseDto(any())).thenReturn(creerProjetResponseDto());

        ProjetResponseDto resultat = projetService.recupererUnProjet(1L);

        assertThat(resultat.getId()).isEqualTo(1L);
    }

    @Test
    void recupererUnProjet_doitLancerUneExceptionSiInexistant() {
        when(projetRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projetService.recupererUnProjet(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Projet introuvable : 99");
    }

    @Test
    void recupererTousLesProjets_doitRetournerLaListe() {
        when(projetRepository.findAll()).thenReturn(List.of(creerProjet()));
        when(projetMapper.convertirProjetEnResponseDto(any())).thenReturn(creerProjetResponseDto());

        List<ProjetResponseDto> resultat = projetService.recupererTousLesProjets();

        assertThat(resultat).hasSize(1);
    }

    @Test
    void mettreAJourUnProjet_doitRetournerLeProjetMisAJour() {
        ProjetRequestDto requestDto = new ProjetRequestDto();
        requestDto.setNom("Projet Modifié");
        requestDto.setDescription("Nouvelle description");
        requestDto.setDateDeDebut(LocalDate.now());

        Projet projet = creerProjet();

        when(projetRepository.findById(1L)).thenReturn(Optional.of(projet));
        when(projetRepository.save(any())).thenReturn(projet);
        when(projetMapper.convertirProjetEnResponseDto(any())).thenReturn(creerProjetResponseDto());

        ProjetResponseDto resultat = projetService.mettreAJourUnProjet(1L, requestDto);

        assertThat(resultat).isNotNull();
    }
}