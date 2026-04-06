package com.edc.pmt.mapper;

import com.edc.pmt.dto.request.MembreProjetRequestDto;
import com.edc.pmt.dto.response.MembreProjetResponseDto;
import com.edc.pmt.entity.MembreProjet;
import com.edc.pmt.entity.Projet;
import com.edc.pmt.entity.Utilisateur;
import com.edc.pmt.enums.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MembreProjetMapperTest {

    private final UtilisateurMapper utilisateurMapper = new UtilisateurMapper();
    private final ProjetMapper projetMapper = new ProjetMapper(utilisateurMapper);
    private final MembreProjetMapper membreProjetMapper = new MembreProjetMapper(projetMapper, utilisateurMapper);

    private Utilisateur creerUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setUsername("Jean Dupont");
        utilisateur.setEmail("jean.dupont@email.com");
        utilisateur.setDateDeCreation(LocalDateTime.now());
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

    @Test
    void convertirRequestDtoEnMembreProjet_doitRetournerUnMembreProjetAvecLeRole() {
        MembreProjetRequestDto requestDto = new MembreProjetRequestDto();
        requestDto.setEmail("jean.dupont@email.com");
        requestDto.setRole(Role.MEMBRE);

        MembreProjet membreProjet = membreProjetMapper.convertirRequestDtoEnMembreProjet(requestDto);

        assertThat(membreProjet).isNotNull();
        assertThat(membreProjet.getRole()).isEqualTo(Role.MEMBRE);
    }

    @Test
    void convertirMembreProjetEnResponseDto_doitRetournerUnResponseDtoAvecLesBonnesValeurs() {
        MembreProjet membreProjet = new MembreProjet();
        membreProjet.setId(1L);
        membreProjet.setRole(Role.ADMINISTRATEUR);
        membreProjet.setDateDeCreation(LocalDateTime.now());
        membreProjet.setUtilisateur(creerUtilisateur());
        membreProjet.setProjet(creerProjet());

        MembreProjetResponseDto responseDto = membreProjetMapper.convertirMembreProjetEnResponseDto(membreProjet);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getRole()).isEqualTo(Role.ADMINISTRATEUR);
        assertThat(responseDto.getUtilisateur().getUsername()).isEqualTo("Jean Dupont");
        assertThat(responseDto.getProjet().getNom()).isEqualTo("Mon Projet");
    }
}