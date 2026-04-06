package com.edc.pmt.controller;

import com.edc.pmt.dto.response.UtilisateurResponseDto;
import com.edc.pmt.service.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UtilisateurController.class)
public class UtilisateurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UtilisateurService utilisateurService;

    private UtilisateurResponseDto creerUtilisateurResponseDto(Long id, String username, String email) {
        UtilisateurResponseDto responseDto = new UtilisateurResponseDto();
        responseDto.setId(id);
        responseDto.setUsername(username);
        responseDto.setEmail(email);
        responseDto.setDateDeCreation(LocalDateTime.now());
        return responseDto;
    }

    @Test
    void recupererTousLesUtilisateurs_doitRetourner200AvecLaListe() throws Exception {
        List<UtilisateurResponseDto> listeUtilisateurs = List.of(
                creerUtilisateurResponseDto(1L, "Jean Dupont", "jean.dupont@email.com"),
                creerUtilisateurResponseDto(2L, "Marie Martin", "marie.martin@email.com")
        );

        when(utilisateurService.recupererTousLesUtilisateurs()).thenReturn(listeUtilisateurs);

        mockMvc.perform(get("/utilisateurs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("Jean Dupont"))
                .andExpect(jsonPath("$[1].username").value("Marie Martin"));
    }

    @Test
    void recupererUnUtilisateur_doitRetourner200AvecLUtilisateur() throws Exception {
        when(utilisateurService.recupererUnUtilisateur(1L))
                .thenReturn(creerUtilisateurResponseDto(1L, "Jean Dupont", "jean.dupont@email.com"));

        mockMvc.perform(get("/utilisateurs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("Jean Dupont"));
    }

    @Test
    void recupererUnUtilisateur_doitRetourner500SiUtilisateurInexistant() throws Exception {
        when(utilisateurService.recupererUnUtilisateur(99L))
                .thenThrow(new RuntimeException("Aucun utilisateur trouvé avec l'identifiant : 99"));

        mockMvc.perform(get("/utilisateurs/99"))
                .andExpect(status().isInternalServerError());
    }
}