package com.edc.pmt.controller;

import com.edc.pmt.dto.request.TacheRequestDto;
import com.edc.pmt.dto.response.ProjetResponseDto;
import com.edc.pmt.dto.response.TacheResponseDto;
import com.edc.pmt.dto.response.UtilisateurResponseDto;
import com.edc.pmt.enums.Priorite;
import com.edc.pmt.service.TacheService;
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

@WebMvcTest(TacheController.class)
public class TacheControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private TacheService tacheService;

        private TacheResponseDto creerTacheResponseDto(Long id, String nom) {
                UtilisateurResponseDto membre = new UtilisateurResponseDto();
                membre.setId(1L);
                membre.setUsername("Jean Dupont");

                ProjetResponseDto projet = new ProjetResponseDto();
                projet.setId(1L);
                projet.setNom("Mon Projet");

                TacheResponseDto responseDto = new TacheResponseDto();
                responseDto.setId(id);
                responseDto.setNom(nom);
                responseDto.setDescription("Description de la tâche");
                responseDto.setDateEcheant(LocalDate.now().plusDays(7));
                responseDto.setPriorite(Priorite.MOYENNE);
                responseDto.setDateDeCreation(LocalDateTime.now());
                responseDto.setProjet(projet);
                responseDto.setMembreAssigne(membre);
                return responseDto;
        }

        private TacheRequestDto creerTacheRequestDto() {
                TacheRequestDto requestDto = new TacheRequestDto();
                requestDto.setNom("Ma Tâche");
                requestDto.setDescription("Description de la tâche");
                requestDto.setDateEcheant(LocalDate.now().plusDays(7));
                requestDto.setPriorite(Priorite.MOYENNE);
                return requestDto;
        }

        @Test
        void creerUneTache_doitRetourner201AvecLaTacheCree() throws Exception {
                when(tacheService.creerUneTache(any(TacheRequestDto.class), eq(1L), eq(1L), eq(1L)))
                                .thenReturn(creerTacheResponseDto(1L, "Ma Tâche"));

                mockMvc.perform(post("/projets/1/taches")
                                .param("membreAssigneId", "1")
                                .param("createurId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(creerTacheRequestDto())))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(1L))
                                .andExpect(jsonPath("$.nom").value("Ma Tâche"));
        }

        @Test
        void recupererLesTachesDeUnProjet_doitRetourner200AvecLaListe() throws Exception {
                List<TacheResponseDto> listeTaches = List.of(
                                creerTacheResponseDto(1L, "Tâche 1"),
                                creerTacheResponseDto(2L, "Tâche 2"));

                when(tacheService.recupererLesTachesDeUnProjet(1L)).thenReturn(listeTaches);

                mockMvc.perform(get("/projets/1/taches"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2))
                                .andExpect(jsonPath("$[0].nom").value("Tâche 1"));
        }

        @Test
        void recupererUneTache_doitRetourner200AvecLaTache() throws Exception {
                when(tacheService.recupererUneTache(1L))
                                .thenReturn(creerTacheResponseDto(1L, "Ma Tâche"));

                mockMvc.perform(get("/projets/1/taches/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1L))
                                .andExpect(jsonPath("$.nom").value("Ma Tâche"));
        }

        @Test
        void mettreAJourUneTache_doitRetourner200AvecLaTacheMiseAJour() throws Exception {
                TacheRequestDto requestDto = creerTacheRequestDto();
                requestDto.setNom("Tâche Modifiée");

                when(tacheService.mettreAJourUneTache(any(TacheRequestDto.class), eq(1L)))
                                .thenReturn(creerTacheResponseDto(1L, "Tâche Modifiée"));

                mockMvc.perform(put("/projets/1/taches/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nom").value("Tâche Modifiée"));
        }
}