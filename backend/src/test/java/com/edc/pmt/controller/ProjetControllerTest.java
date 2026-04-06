package com.edc.pmt.controller;

import com.edc.pmt.dto.request.ProjetRequestDto;
import com.edc.pmt.dto.response.ProjetResponseDto;
import com.edc.pmt.dto.response.UtilisateurResponseDto;
import com.edc.pmt.service.ProjetService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjetController.class)
public class ProjetControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private ProjetService projetService;

        private ProjetResponseDto creerProjetResponseDto(Long id, String nom) {
                UtilisateurResponseDto administrateur = new UtilisateurResponseDto();
                administrateur.setId(1L);
                administrateur.setUsername("Jean Dupont");
                administrateur.setEmail("jean.dupont@email.com");

                ProjetResponseDto responseDto = new ProjetResponseDto();
                responseDto.setId(id);
                responseDto.setNom(nom);
                responseDto.setDescription("Description du projet");
                responseDto.setDateDeDebut(LocalDate.now());
                responseDto.setDateDeCreation(LocalDateTime.now());
                responseDto.setAdministrateur(administrateur);
                return responseDto;
        }

        private ProjetRequestDto creerProjetRequestDto() {
                ProjetRequestDto requestDto = new ProjetRequestDto();
                requestDto.setNom("Mon Projet");
                requestDto.setDescription("Description du projet");
                requestDto.setDateDeDebut(LocalDate.now());
                return requestDto;
        }

        @Test
        void creerUnProjet_doitRetourner201AvecLeProjetCree() throws Exception {
                when(projetService.creerUnProjet(any(ProjetRequestDto.class), eq(1L)))
                                .thenReturn(creerProjetResponseDto(1L, "Mon Projet"));

                mockMvc.perform(post("/projets")
                                .param("administrateurId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(creerProjetRequestDto())))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(1L))
                                .andExpect(jsonPath("$.nom").value("Mon Projet"));
        }

        @Test
        void recupererTousLesProjets_doitRetourner200AvecLaListe() throws Exception {
                List<ProjetResponseDto> listeProjets = List.of(
                                creerProjetResponseDto(1L, "Projet Alpha"),
                                creerProjetResponseDto(2L, "Projet Beta"));

                when(projetService.recupererTousLesProjets()).thenReturn(listeProjets);

                mockMvc.perform(get("/projets"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2))
                                .andExpect(jsonPath("$[0].nom").value("Projet Alpha"));
        }

        @Test
        void recupererUnProjet_doitRetourner200AvecLeProjet() throws Exception {
                when(projetService.recupererUnProjet(1L))
                                .thenReturn(creerProjetResponseDto(1L, "Mon Projet"));

                mockMvc.perform(get("/projets/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1L))
                                .andExpect(jsonPath("$.nom").value("Mon Projet"));
        }

        @Test
        void recupererUnProjet_doitRetourner500SiProjetInexistant() throws Exception {
                when(projetService.recupererUnProjet(99L))
                                .thenThrow(new RuntimeException("Projet introuvable : 99"));

                mockMvc.perform(get("/projets/99"))
                                .andExpect(status().isInternalServerError());
        }

        @Test
        void recupererProjetsUtilisateur_doitRetourner200AvecLesProjets() throws Exception {
                List<ProjetResponseDto> listeProjets = List.of(creerProjetResponseDto(1L, "Mon Projet"));

                when(projetService.recupererLesProjetsDeUnUtilisateur(1L)).thenReturn(listeProjets);

                mockMvc.perform(get("/projets/utilisateur/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));
        }

        @Test
        void mettreAJourUnProjet_doitRetourner200AvecLeProjetMisAJour() throws Exception {
                ProjetRequestDto requestDto = creerProjetRequestDto();
                requestDto.setNom("Projet Modifié");

                when(projetService.mettreAJourUnProjet(eq(1L), any(ProjetRequestDto.class)))
                                .thenReturn(creerProjetResponseDto(1L, "Projet Modifié"));

                mockMvc.perform(put("/projets/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nom").value("Projet Modifié"));
        }
}