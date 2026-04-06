package com.edc.pmt.controller;

import com.edc.pmt.dto.request.UtilisateurRequestDto;
import com.edc.pmt.dto.response.UtilisateurResponseDto;
import com.edc.pmt.service.UtilisateurService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private UtilisateurService utilisateurService;

        private UtilisateurResponseDto creerUtilisateurResponseDto() {
                UtilisateurResponseDto responseDto = new UtilisateurResponseDto();
                responseDto.setId(1L);
                responseDto.setUsername("Jean Dupont");
                responseDto.setEmail("jean.dupont@email.com");
                responseDto.setDateDeCreation(LocalDateTime.now());
                return responseDto;
        }

        @Test
        void inscrireUnUtilisateur_doitRetourner201AvecLUtilisateurCree() throws Exception {
                UtilisateurRequestDto requestDto = new UtilisateurRequestDto();
                requestDto.setUsername("Jean Dupont");
                requestDto.setEmail("jean.dupont@email.com");
                requestDto.setPassword("motdepasse123");

                when(utilisateurService.inscrireUnUtilisateur(any(UtilisateurRequestDto.class)))
                                .thenReturn(creerUtilisateurResponseDto());

                mockMvc.perform(post("/auth/inscription")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(1L))
                                .andExpect(jsonPath("$.username").value("Jean Dupont"))
                                .andExpect(jsonPath("$.email").value("jean.dupont@email.com"));
        }

        @Test
        void connecterUnUtilisateur_doitRetourner200AvecLUtilisateurConnecte() throws Exception {
                UtilisateurRequestDto requestDto = new UtilisateurRequestDto();
                requestDto.setUsername("Jean Dupont");
                requestDto.setEmail("jean.dupont@email.com");
                requestDto.setPassword("motdepasse123");

                when(utilisateurService.connecterUnUtilisateur(
                                eq("jean.dupont@email.com"),
                                eq("motdepasse123")))
                                .thenReturn(creerUtilisateurResponseDto());

                mockMvc.perform(post("/auth/connexion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email").value("jean.dupont@email.com"));
        }

        @Test
        void connecterUnUtilisateur_doitRetourner500SiMotDePasseIncorrect() throws Exception {
                UtilisateurRequestDto requestDto = new UtilisateurRequestDto();
                requestDto.setUsername("Jean Dupont");
                requestDto.setEmail("jean.dupont@email.com");
                requestDto.setPassword("mauvaisMotDePasse");

                when(utilisateurService.connecterUnUtilisateur(any(), any()))
                                .thenThrow(new RuntimeException("Le mot de passe est incorrect."));

                mockMvc.perform(post("/auth/connexion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
                                .andExpect(status().isInternalServerError());
        }
}