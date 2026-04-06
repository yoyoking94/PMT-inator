package com.edc.pmt.mapper;

import com.edc.pmt.dto.request.ProjetRequestDto;
import com.edc.pmt.dto.response.ProjetResponseDto;
import com.edc.pmt.entity.Projet;
import com.edc.pmt.entity.Utilisateur;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjetMapperTest {

    private final UtilisateurMapper utilisateurMapper = new UtilisateurMapper();
    private final ProjetMapper projetMapper = new ProjetMapper(utilisateurMapper);

    private Utilisateur creerUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setUsername("Jean Dupont");
        utilisateur.setEmail("jean.dupont@email.com");
        utilisateur.setDateDeCreation(LocalDateTime.now());
        return utilisateur;
    }

    @Test
    void convertirRequestDtoEnProjet_doitRetournerUnProjetAvecLesBonnesValeurs() {
        ProjetRequestDto requestDto = new ProjetRequestDto();
        requestDto.setNom("Mon Projet");
        requestDto.setDescription("Description du projet");
        requestDto.setDateDeDebut(LocalDate.of(2026, 1, 1));

        Utilisateur administrateur = creerUtilisateur();

        Projet projet = projetMapper.convertirRequestDtoEnProjet(requestDto, administrateur);

        assertThat(projet).isNotNull();
        assertThat(projet.getNom()).isEqualTo("Mon Projet");
        assertThat(projet.getDescription()).isEqualTo("Description du projet");
        assertThat(projet.getDateDeDebut()).isEqualTo(LocalDate.of(2026, 1, 1));
        assertThat(projet.getAdministrateur().getUsername()).isEqualTo("Jean Dupont");
    }

    @Test
    void convertirProjetEnResponseDto_doitRetournerUnResponseDtoAvecLesBonnesValeurs() {
        Projet projet = new Projet();
        projet.setId(1L);
        projet.setNom("Mon Projet");
        projet.setDescription("Description du projet");
        projet.setDateDeDebut(LocalDate.of(2026, 1, 1));
        projet.setDateDeCreation(LocalDateTime.now());
        projet.setAdministrateur(creerUtilisateur());

        ProjetResponseDto responseDto = projetMapper.convertirProjetEnResponseDto(projet);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getNom()).isEqualTo("Mon Projet");
        assertThat(responseDto.getAdministrateur()).isNotNull();
        assertThat(responseDto.getAdministrateur().getUsername()).isEqualTo("Jean Dupont");
    }
}