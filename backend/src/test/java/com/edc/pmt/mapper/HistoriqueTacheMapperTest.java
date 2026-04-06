package com.edc.pmt.mapper;

import com.edc.pmt.dto.request.HistoriqueTacheRequestDto;
import com.edc.pmt.dto.response.HistoriqueTacheResponseDto;
import com.edc.pmt.entity.HistoriqueTache;
import com.edc.pmt.entity.Projet;
import com.edc.pmt.entity.Tache;
import com.edc.pmt.entity.Utilisateur;
import com.edc.pmt.enums.Priorite;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class HistoriqueTacheMapperTest {

    private final UtilisateurMapper utilisateurMapper = new UtilisateurMapper();
    private final ProjetMapper projetMapper = new ProjetMapper(utilisateurMapper);
    private final TacheMapper tacheMapper = new TacheMapper(projetMapper, utilisateurMapper);
    private final HistoriqueTacheMapper historiqueTacheMapper =
            new HistoriqueTacheMapper(tacheMapper, utilisateurMapper);

    private Utilisateur creerUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setUsername("Jean Dupont");
        utilisateur.setEmail("jean.dupont@email.com");
        utilisateur.setDateDeCreation(LocalDateTime.now());
        return utilisateur;
    }

    private Tache creerTache() {
        Projet projet = new Projet();
        projet.setId(1L);
        projet.setNom("Mon Projet");
        projet.setDescription("Description");
        projet.setDateDeDebut(LocalDate.now());
        projet.setDateDeCreation(LocalDateTime.now());
        projet.setAdministrateur(creerUtilisateur());

        Tache tache = new Tache();
        tache.setId(1L);
        tache.setNom("Ma Tâche");
        tache.setDescription("Description de la tâche");
        tache.setDateEcheant(LocalDate.now().plusDays(7));
        tache.setPriorite(Priorite.MOYENNE);
        tache.setDateDeCreation(LocalDateTime.now());
        tache.setProjet(projet);
        tache.setMembreAssigne(creerUtilisateur());
        return tache;
    }

    @Test
    void convertirRequestDtoEnHistoriqueTache_doitRetournerUnHistoriqueAvecLesBonnesValeurs() {
        HistoriqueTacheRequestDto requestDto = new HistoriqueTacheRequestDto();
        requestDto.setTacheId(1L);
        requestDto.setModifieParId(1L);
        requestDto.setChampModifie("statut");
        requestDto.setAncienneValeur("EN_COURS");
        requestDto.setNouvelleValeur("TERMINE");

        HistoriqueTache historiqueTache =
                historiqueTacheMapper.convertirRequestDtoEnHistoriqueTache(requestDto);

        assertThat(historiqueTache).isNotNull();
        assertThat(historiqueTache.getChampModifie()).isEqualTo("statut");
        assertThat(historiqueTache.getAncienneValeur()).isEqualTo("EN_COURS");
        assertThat(historiqueTache.getNouvelleValeur()).isEqualTo("TERMINE");
        assertThat(historiqueTache.getTache().getId()).isEqualTo(1L);
        assertThat(historiqueTache.getModifiePar().getId()).isEqualTo(1L);
    }

    @Test
    void convertirHistoriqueTacheEnResponseDto_doitRetournerUnResponseDtoAvecLesBonnesValeurs() {
        HistoriqueTache historiqueTache = new HistoriqueTache();
        historiqueTache.setId(1L);
        historiqueTache.setTache(creerTache());
        historiqueTache.setModifiePar(creerUtilisateur());
        historiqueTache.setChampModifie("priorite");
        historiqueTache.setAncienneValeur("BASSE");
        historiqueTache.setNouvelleValeur("HAUTE");
        historiqueTache.setDateDeModification(LocalDateTime.now());

        HistoriqueTacheResponseDto responseDto =
                historiqueTacheMapper.convertirHistoriqueTacheEnResponseDto(historiqueTache);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getChampModifie()).isEqualTo("priorite");
        assertThat(responseDto.getAncienneValeur()).isEqualTo("BASSE");
        assertThat(responseDto.getNouvelleValeur()).isEqualTo("HAUTE");
        assertThat(responseDto.getModifiePar().getUsername()).isEqualTo("Jean Dupont");
        assertThat(responseDto.getTache().getNom()).isEqualTo("Ma Tâche");
    }
}