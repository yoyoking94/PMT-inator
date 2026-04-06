package com.edc.pmt.service;

import com.edc.pmt.dto.request.MembreProjetRequestDto;
import com.edc.pmt.dto.response.MembreProjetResponseDto;
import com.edc.pmt.entity.MembreProjet;
import com.edc.pmt.entity.Projet;
import com.edc.pmt.entity.Utilisateur;
import com.edc.pmt.enums.Role;
import com.edc.pmt.mapper.MembreProjetMapper;
import com.edc.pmt.repository.MembreProjetRepository;
import com.edc.pmt.repository.ProjetRepository;
import com.edc.pmt.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MembreProjetServiceTest {

    @Mock
    private MembreProjetRepository membreProjetRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private ProjetRepository projetRepository;

    @Mock
    private MembreProjetMapper membreProjetMapper;

    @InjectMocks
    private MembreProjetService membreProjetService;

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

    @Test
    void inviterUnMembre_doitSauvegarderEtRetournerLeMembre() {
        MembreProjetRequestDto requestDto = new MembreProjetRequestDto();
        requestDto.setEmail("jean.dupont@email.com");
        requestDto.setRole(Role.MEMBRE);

        MembreProjet administrateur = new MembreProjet();
        administrateur.setRole(Role.ADMINISTRATEUR);

        MembreProjet membreProjet = new MembreProjet();
        membreProjet.setId(1L);
        membreProjet.setRole(Role.MEMBRE);

        MembreProjetResponseDto responseDto = new MembreProjetResponseDto();
        responseDto.setId(1L);
        responseDto.setRole(Role.MEMBRE);

        when(membreProjetRepository.findByProjetIdAndUtilisateurId(1L, 1L))
                .thenReturn(Optional.of(administrateur));
        when(utilisateurRepository.findByEmail("jean.dupont@email.com"))
                .thenReturn(Optional.of(creerUtilisateur()));
        when(projetRepository.findById(1L)).thenReturn(Optional.of(creerProjet()));
        when(membreProjetMapper.convertirRequestDtoEnMembreProjet(any())).thenReturn(membreProjet);
        when(membreProjetRepository.save(any())).thenReturn(membreProjet);
        when(membreProjetMapper.convertirMembreProjetEnResponseDto(any())).thenReturn(responseDto);

        MembreProjetResponseDto resultat = membreProjetService.inviterUnMembre(1L, 1L, requestDto);

        assertThat(resultat).isNotNull();
        assertThat(resultat.getRole()).isEqualTo(Role.MEMBRE);
    }

    @Test
    void inviterUnMembre_doitLancerUneExceptionSiUtilisateurInexistant() {
        MembreProjetRequestDto requestDto = new MembreProjetRequestDto();
        requestDto.setEmail("inconnu@email.com");
        requestDto.setRole(Role.MEMBRE);

        MembreProjet administrateur = new MembreProjet();
        administrateur.setRole(Role.ADMINISTRATEUR);

        when(membreProjetRepository.findByProjetIdAndUtilisateurId(1L, 1L))
                .thenReturn(Optional.of(administrateur));
        when(utilisateurRepository.findByEmail("inconnu@email.com"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> membreProjetService.inviterUnMembre(1L, 1L, requestDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Aucun utilisateur trouvé avec l'email");
    }

    @Test
    void inviterUnMembre_doitLancerUneExceptionSiMembreDejaExistant() {
        MembreProjetRequestDto requestDto = new MembreProjetRequestDto();
        requestDto.setEmail("jean.dupont@email.com");
        requestDto.setRole(Role.MEMBRE);

        MembreProjet administrateur = new MembreProjet();
        administrateur.setRole(Role.ADMINISTRATEUR);

        when(membreProjetRepository.findByProjetIdAndUtilisateurId(1L, 1L))
                .thenReturn(Optional.of(administrateur));
        when(utilisateurRepository.findByEmail("jean.dupont@email.com"))
                .thenReturn(Optional.of(creerUtilisateur()));
        when(membreProjetRepository.existsByUtilisateurIdAndProjetId(1L, 1L))
                .thenReturn(true);

        assertThatThrownBy(() -> membreProjetService.inviterUnMembre(1L, 1L, requestDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Cet utilisateur est déjà membre du projet");
    }
}