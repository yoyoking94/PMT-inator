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

import com.edc.pmt.dto.request.TacheRequestDto;
import com.edc.pmt.dto.response.TacheResponseDto;
import com.edc.pmt.service.TacheService;

@RestController
@RequestMapping("/projets/{projetId}/taches")
public class TacheController {

    private final TacheService tacheService;

    public TacheController(TacheService tacheService) {
        this.tacheService = tacheService;
    }

    @PostMapping
    public ResponseEntity<TacheResponseDto> creerUneTache(
            @RequestBody TacheRequestDto tacheRequestDto,
            @PathVariable Long projetId,
            @RequestParam Long membreAssigneId,
            @RequestParam Long createurId) {
        TacheResponseDto tacheResponseDto = tacheService.creerUneTache(
                tacheRequestDto, projetId, membreAssigneId, createurId);
        return ResponseEntity.status(HttpStatus.CREATED).body(tacheResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<TacheResponseDto>> recupererLesTachesDeUnProjet(@PathVariable Long projetId) {
        List<TacheResponseDto> listeTaches = tacheService.recupererLesTachesDeUnProjet(projetId);
        return ResponseEntity.ok(listeTaches);
    }

    @PutMapping("/{tacheId}")
    public ResponseEntity<TacheResponseDto> mettreAJourUneTache(@RequestBody TacheRequestDto tacheRequestDto,
            @PathVariable Long tacheId) {
        TacheResponseDto tacheResponseDto = tacheService.mettreAJourUneTache(tacheRequestDto, tacheId);
        return ResponseEntity.ok(tacheResponseDto);
    }

    @GetMapping("/{tacheId}")
    public ResponseEntity<TacheResponseDto> recupererUneTache(@PathVariable Long tacheId) {
        TacheResponseDto tacheResponseDto = tacheService.recupererUneTache(tacheId);
        return ResponseEntity.ok(tacheResponseDto);
    }

}
