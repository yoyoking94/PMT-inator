package com.edc.pmt.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edc.pmt.dto.response.UtilisateurResponseDto;
import com.edc.pmt.service.UtilisateurService;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public ResponseEntity<List<UtilisateurResponseDto>> recupererTousLesUtilisateurs() {
        List<UtilisateurResponseDto> listeUtilisateurs = utilisateurService.recupererTousLesUtilisateurs();
        return ResponseEntity.ok(listeUtilisateurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurResponseDto> recupererUnUtilisateur(@PathVariable("id") Long id) {
        UtilisateurResponseDto utilisateurResponseDto = utilisateurService.recupererUnUtilisateur(id);
        return ResponseEntity.ok(utilisateurResponseDto);
    }
}
