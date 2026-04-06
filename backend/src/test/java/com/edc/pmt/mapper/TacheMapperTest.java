package com.edc.pmt.mapper;

import com.edc.pmt.dto.request.TacheRequestDto;
import com.edc.pmt.dto.response.TacheResponseDto;
import com.edc.pmt.entity.Projet;
import com.edc.pmt.entity.Tache;
import com.edc.pmt.entity.Utilisateur;
import com.edc.pmt.enums.Priorite;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TacheMapperTest {

    private final UtilisateurMapper utilisateurMapper = new UtilisateurMapper();
    private final ProjetMapper projetMapper = new ProjetMapper(utilisateurMapper);
    private final TacheMapper tacheMapper = new TacheMapper(projetMapper, utilisateurMapper);

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
    void convertirRequestDtoEnTache_doitRetournerUneTacheAvecLesBonnesValeurs() {
        TacheRequestDto requestDto = new TacheRequestDto();
        requestDto.setNom("Ma Tâche");
        requestDto.setDescription("Description de la tâche");
        requestDto.setDateEcheant(LocalDate.of(2026, 6, 1));
        requestDto.setPriorite(Priorite.HAUTE);

        Tache tache = tacheMapper.convertirRequestDtoEnTache(requestDto, creerProjet(), creerUtilisateur());

        assertThat(tache).isNotNull();
        assertThat(tache.getNom()).isEqualTo("Ma Tâche");
        assertThat(tache.getDescription()).isEqualTo("Description de la tâche");
        assertThat(tache.getPriorite()).isEqualTo(Priorite.HAUTE);
        assertThat(tache.getProjet().getNom()).isEqualTo("Mon Projet");
        assertThat(tache.getMembreAssigne().getUsername()).isEqualTo("Jean Dupont");
    }

    @Test
    void convertirTacheEnResponseDto_doitRetournerUnResponseDtoAvecLesBonnesValeurs() {
        Tache tache = new Tache();
        tache.setId(1L);
        tache.setNom("Ma Tâche");
        tache.setDescription("Description de la tâche");
        tache.setDateEcheant(LocalDate.of(2026, 6, 1));
        tache.setPriorite(Priorite.MOYENNE);
        tache.setDateDeCreation(LocalDateTime.now());
        tache.setProjet(creerProjet());
        tache.setMembreAssigne(creerUtilisateur());

        TacheResponseDto responseDto = tacheMapper.convertirTacheEnResponseDto(tache);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getNom()).isEqualTo("Ma Tâche");
        assertThat(responseDto.getPriorite()).isEqualTo(Priorite.MOYENNE);
        assertThat(responseDto.getProjet().getNom()).isEqualTo("Mon Projet");
        assertThat(responseDto.getMembreAssigne().getUsername()).isEqualTo("Jean Dupont");
    }
}