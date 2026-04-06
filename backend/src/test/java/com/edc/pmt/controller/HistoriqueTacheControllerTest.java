package com.edc.pmt.controller;

import com.edc.pmt.dto.request.HistoriqueTacheRequestDto;
import com.edc.pmt.dto.response.HistoriqueTacheResponseDto;
import com.edc.pmt.dto.response.TacheResponseDto;
import com.edc.pmt.dto.response.UtilisateurResponseDto;
import com.edc.pmt.service.HistoriqueTacheService;
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

@WebMvcTest(HistoriqueTacheController.class)
public class HistoriqueTacheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HistoriqueTacheService historiqueTacheService;

    private HistoriqueTacheResponseDto creerHistoriqueResponseDto(Long id) {
        UtilisateurResponseDto modifiePar = new UtilisateurResponseDto();
        modifiePar.setId(1L);
        modifiePar.setUsername("Jean Dupont");

        TacheResponseDto tache = new TacheResponseDto();
        tache.setId(1L);
        tache.setNom("Ma Tâche");

        HistoriqueTacheResponseDto responseDto = new HistoriqueTacheResponseDto();
        responseDto.setId(id);
        responseDto.setTache(tache);
        responseDto.setModifiePar(modifiePar);
        responseDto.setChampModifie("statut");
        responseDto.setAncienneValeur("EN_COURS");
        responseDto.setNouvelleValeur("TERMINE");
        responseDto.setDateDeModification(LocalDateTime.now());
        return responseDto;
    }

    private HistoriqueTacheRequestDto creerHistoriqueRequestDto() {
        HistoriqueTacheRequestDto requestDto = new HistoriqueTacheRequestDto();
        requestDto.setTacheId(1L);
        requestDto.setModifieParId(1L);
        requestDto.setChampModifie("statut");
        requestDto.setAncienneValeur("EN_COURS");
        requestDto.setNouvelleValeur("TERMINE");
        return requestDto;
    }

    @Test
    void enregistrerUneModification_doitRetourner201AvecLHistoriqueEnregistre() throws Exception {
        when(historiqueTacheService.enregistreModification(eq(1L), any(HistoriqueTacheRequestDto.class)))
                .thenReturn(creerHistoriqueResponseDto(1L));

        mockMvc.perform(post("/taches/1/historiques")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creerHistoriqueRequestDto())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.champModifie").value("statut"))
                .andExpect(jsonPath("$.ancienneValeur").value("EN_COURS"))
                .andExpect(jsonPath("$.nouvelleValeur").value("TERMINE"));
    }

    @Test
    void recupererHistoriqueDeUneTache_doitRetourner200AvecLaListe() throws Exception {
        List<HistoriqueTacheResponseDto> listeHistorique = List.of(
                creerHistoriqueResponseDto(1L),
                creerHistoriqueResponseDto(2L));

        when(historiqueTacheService.recupererLesHistoriquesDeUneTache(1L)).thenReturn(listeHistorique);

        mockMvc.perform(get("/taches/1/historiques"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}