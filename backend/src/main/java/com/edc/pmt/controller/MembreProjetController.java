package com.edc.pmt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edc.pmt.dto.request.MembreProjetRequestDto;
import com.edc.pmt.dto.response.MembreProjetResponseDto;
import com.edc.pmt.service.MembreProjetService;

@RestController
@RequestMapping("/projets/{projetId}/membres")
public class MembreProjetController {

    private final MembreProjetService membreProjetService;

    public MembreProjetController(MembreProjetService membreProjetService) {
        this.membreProjetService = membreProjetService;
    }

    @PostMapping // ← plus de chemin ici, @RequestMapping s'en occupe déjà
    public ResponseEntity<MembreProjetResponseDto> inviterUnMembre(
            @PathVariable Long projetId,
            @RequestParam Long administrateurId,
            @RequestBody MembreProjetRequestDto membreProjetRequestDto) {

        MembreProjetResponseDto membreProjetResponseDto = membreProjetService
                .inviterUnMembre(projetId, administrateurId, membreProjetRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(membreProjetResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<MembreProjetResponseDto>> recupererMembreDeUnProjet(
            @PathVariable Long projetId) {
        List<MembreProjetResponseDto> listeMembre = membreProjetService
                .recupererLesMembresDeUnProjet(projetId);
        return ResponseEntity.ok(listeMembre);
    }
}