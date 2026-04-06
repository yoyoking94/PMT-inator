package com.edc.pmt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edc.pmt.dto.request.ProjetRequestDto;
import com.edc.pmt.dto.response.ProjetResponseDto;
import com.edc.pmt.service.ProjetService;

@RestController
@RequestMapping("/projets")
public class ProjetController {

    private final ProjetService projetService;

    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }

    @PostMapping
    public ResponseEntity<ProjetResponseDto> creerUnProjet(@RequestBody ProjetRequestDto projetRequestDto,
            @RequestParam Long administrateurId) {
        ProjetResponseDto projetResponseDto = projetService.creerUnProjet(projetRequestDto, administrateurId);
        return ResponseEntity.status(HttpStatus.CREATED).body(projetResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProjetResponseDto>> recupererTousLesProjets() {
        List<ProjetResponseDto> listeProjets = projetService.recupererTousLesProjets();
        return ResponseEntity.ok(listeProjets);
    }

    @GetMapping("/{projetId}")
    public ResponseEntity<ProjetResponseDto> recupererUnProjet(@PathVariable Long projetId) {
        ProjetResponseDto projet = projetService.recupererUnProjet(projetId);
        return ResponseEntity.ok(projet);
    }

    @GetMapping("/utilisateur/{administrateurId}")
    public ResponseEntity<List<ProjetResponseDto>> recupererProjetsUtilisateur(@PathVariable Long administrateurId) {
        List<ProjetResponseDto> listeProjets = projetService.recupererLesProjetsDeUnUtilisateur(administrateurId);
        return ResponseEntity.ok(listeProjets);
    }

    @PutMapping("/{projetId}")
    public ResponseEntity<ProjetResponseDto> mettreAJourUnProjet(@PathVariable Long projetId,
            @RequestBody ProjetRequestDto projetRequestDto) {
        ProjetResponseDto projetResponseDto = projetService.mettreAJourUnProjet(projetId, projetRequestDto);
        return ResponseEntity.ok(projetResponseDto);
    }
}
