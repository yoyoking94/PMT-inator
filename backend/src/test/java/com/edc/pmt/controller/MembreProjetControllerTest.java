package com.edc.pmt.controller;

import com.edc.pmt.dto.request.MembreProjetRequestDto;
import com.edc.pmt.dto.response.MembreProjetResponseDto;
import com.edc.pmt.dto.response.ProjetResponseDto;
import com.edc.pmt.dto.response.UtilisateurResponseDto;
import com.edc.pmt.enums.Role;
import com.edc.pmt.service.MembreProjetService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MembreProjetController.class)
public class MembreProjetControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private MembreProjetService membreProjetService;

        private MembreProjetResponseDto creerMembreProjetResponseDto(Long id) {
                UtilisateurResponseDto utilisateur = new UtilisateurResponseDto();
                utilisateur.setId(1L);
                utilisateur.setUsername("Jean Dupont");
                utilisateur.setEmail("jean.dupont@email.com");

                ProjetResponseDto projet = new ProjetResponseDto();
                projet.setId(1L);
                projet.setNom("Mon Projet");

                MembreProjetResponseDto responseDto = new MembreProjetResponseDto();
                responseDto.setId(id);
                responseDto.setUtilisateur(utilisateur);
                responseDto.setRole(Role.MEMBRE);
                responseDto.setDateDeCreation(LocalDateTime.now());
                return responseDto;
        }

        @Test
        void inviteUnMembre_doitRetourner201AvecLeMembreCree() throws Exception {
                MembreProjetRequestDto requestDto = new MembreProjetRequestDto();
                requestDto.setEmail("jean.dupont@email.com");
                requestDto.setRole(Role.MEMBRE);

                when(membreProjetService.inviterUnMembre(eq(1L), eq(1L), any(MembreProjetRequestDto.class)))
                                .thenReturn(creerMembreProjetResponseDto(1L));

                mockMvc.perform(post("/projets/1/membres")
                                .param("administrateurId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(1L))
                                .andExpect(jsonPath("$.role").value("MEMBRE"));
        }

        @Test
        void recupererMembreDeUnProjet_doitRetourner200AvecLaListe() throws Exception {
                List<MembreProjetResponseDto> listeMembres = List.of(
                                creerMembreProjetResponseDto(1L),
                                creerMembreProjetResponseDto(2L));

                when(membreProjetService.recupererLesMembresDeUnProjet(1L)).thenReturn(listeMembres);

                mockMvc.perform(get("/projets/1/membres"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2));
        }

        @Test
        void inviteUnMembre_doitRetourner500SiMembreDejaExistant() throws Exception {
                MembreProjetRequestDto requestDto = new MembreProjetRequestDto();
                requestDto.setEmail("jean.dupont@email.com");
                requestDto.setRole(Role.MEMBRE);

                when(membreProjetService.inviterUnMembre(any(), any(), any()))
                                .thenThrow(new RuntimeException("Cet utilisateur est déjà membre du projet"));

                mockMvc.perform(post("/projets/1/membres")
                                .param("administrateurId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
                                .andExpect(status().isInternalServerError());
        }
}