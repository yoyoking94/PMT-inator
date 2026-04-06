package com.edc.pmt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edc.pmt.dto.request.HistoriqueTacheRequestDto;
import com.edc.pmt.dto.response.HistoriqueTacheResponseDto;
import com.edc.pmt.service.HistoriqueTacheService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/taches/{tacheId}/historiques")
public class HistoriqueTacheController {
    private final HistoriqueTacheService historiqueTacheService;

    public HistoriqueTacheController(HistoriqueTacheService historiqueTacheService) {
        this.historiqueTacheService = historiqueTacheService;
    }

    @PostMapping
    public ResponseEntity<HistoriqueTacheResponseDto> enregistrerUneModification(
            @Valid @RequestBody HistoriqueTacheRequestDto historiqueTacheRequestDto,
            @PathVariable("tacheId") Long tacheId) {
        HistoriqueTacheResponseDto historiqueTacheResponseDto = historiqueTacheService.enregistreModification(tacheId,
                historiqueTacheRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(historiqueTacheResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<HistoriqueTacheResponseDto>> recupererHistoriqueDeUneTache(
            @PathVariable("tacheId") Long tacheId) {
        List<HistoriqueTacheResponseDto> listeHistorique = historiqueTacheService
                .recupererLesHistoriquesDeUneTache(tacheId);
        return ResponseEntity.ok(listeHistorique);
    }
}
