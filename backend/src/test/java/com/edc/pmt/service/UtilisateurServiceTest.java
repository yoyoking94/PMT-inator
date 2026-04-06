package com.edc.pmt.service;

import com.edc.pmt.dto.request.UtilisateurRequestDto;
import com.edc.pmt.dto.response.UtilisateurResponseDto;
import com.edc.pmt.entity.Utilisateur;
import com.edc.pmt.mapper.UtilisateurMapper;
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
public class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private UtilisateurMapper utilisateurMapper;

    @InjectMocks
    private UtilisateurService utilisateurService;

    private Utilisateur creerUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setUsername("Jean Dupont");
        utilisateur.setEmail("jean.dupont@email.com");
        utilisateur.setPassword("motdepasse123");
        utilisateur.setDateDeCreation(LocalDateTime.now());
        return utilisateur;
    }

    private UtilisateurResponseDto creerUtilisateurResponseDto() {
        UtilisateurResponseDto responseDto = new UtilisateurResponseDto();
        responseDto.setId(1L);
        responseDto.setUsername("Jean Dupont");
        responseDto.setEmail("jean.dupont@email.com");
        responseDto.setDateDeCreation(LocalDateTime.now());
        return responseDto;
    }

    @Test
    void inscrireUnUtilisateur_doitSauvegarderEtRetournerLUtilisateur() {
        UtilisateurRequestDto requestDto = new UtilisateurRequestDto();
        requestDto.setUsername("Jean Dupont");
        requestDto.setEmail("jean.dupont@email.com");
        requestDto.setPassword("motdepasse123");

        Utilisateur utilisateur = creerUtilisateur();

        when(utilisateurMapper.convertirRequeteDtoEnUtilisateur(any())).thenReturn(utilisateur);
        when(utilisateurRepository.save(any())).thenReturn(utilisateur);
        when(utilisateurMapper.convertirUtilisateurEnResponseDto(utilisateur))
                .thenReturn(creerUtilisateurResponseDto());

        UtilisateurResponseDto resultat = utilisateurService.inscrireUnUtilisateur(requestDto);

        assertThat(resultat).isNotNull();
        assertThat(resultat.getUsername()).isEqualTo("Jean Dupont");
        assertThat(resultat.getEmail()).isEqualTo("jean.dupont@email.com");
    }

    @Test
    void connecterUnUtilisateur_doitRetournerLUtilisateurSiEmailEtMotDePasseCorrects() {
        Utilisateur utilisateur = creerUtilisateur();

        when(utilisateurRepository.findByEmail("jean.dupont@email.com"))
                .thenReturn(Optional.of(utilisateur));
        when(utilisateurMapper.convertirUtilisateurEnResponseDto(utilisateur))
                .thenReturn(creerUtilisateurResponseDto());

        UtilisateurResponseDto resultat = utilisateurService.connecterUnUtilisateur(
                "jean.dupont@email.com", "motdepasse123");

        assertThat(resultat).isNotNull();
        assertThat(resultat.getEmail()).isEqualTo("jean.dupont@email.com");
    }

    @Test
    void connecterUnUtilisateur_doitLancerUneExceptionSiEmailInexistant() {
        when(utilisateurRepository.findByEmail("inconnu@email.com"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                utilisateurService.connecterUnUtilisateur("inconnu@email.com", "motdepasse123"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Aucun utilisateur trouvé avec cet email");
    }

    @Test
    void connecterUnUtilisateur_doitLancerUneExceptionSiMotDePasseIncorrect() {
        Utilisateur utilisateur = creerUtilisateur();

        when(utilisateurRepository.findByEmail("jean.dupont@email.com"))
                .thenReturn(Optional.of(utilisateur));

        assertThatThrownBy(() ->
                utilisateurService.connecterUnUtilisateur("jean.dupont@email.com", "mauvaisMotDePasse"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Le mot de passe est incorrect.");
    }

    @Test
    void recupererTousLesUtilisateurs_doitRetournerLaListe() {
        List<Utilisateur> listeUtilisateurs = List.of(creerUtilisateur());

        when(utilisateurRepository.findAll()).thenReturn(listeUtilisateurs);
        when(utilisateurMapper.convertirUtilisateurEnResponseDto(any()))
                .thenReturn(creerUtilisateurResponseDto());

        List<UtilisateurResponseDto> resultat = utilisateurService.recupererTousLesUtilisateurs();

        assertThat(resultat).hasSize(1);
    }

    @Test
    void recupererUnUtilisateur_doitRetournerLUtilisateur() {
        Utilisateur utilisateur = creerUtilisateur();

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(utilisateurMapper.convertirUtilisateurEnResponseDto(utilisateur))
                .thenReturn(creerUtilisateurResponseDto());

        UtilisateurResponseDto resultat = utilisateurService.recupererUnUtilisateur(1L);

        assertThat(resultat.getId()).isEqualTo(1L);
    }

    @Test
    void recupererUnUtilisateur_doitLancerUneExceptionSiInexistant() {
        when(utilisateurRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> utilisateurService.recupererUnUtilisateur(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Aucun utilisateur trouvé avec l'identifiant : 99");
    }
}