package com.edc.pmt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edc.pmt.dto.request.UtilisateurRequestDto;
import com.edc.pmt.dto.response.UtilisateurResponseDto;
import com.edc.pmt.service.UtilisateurService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UtilisateurService utilisateurService;

    public AuthController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/inscription")
    public ResponseEntity<UtilisateurResponseDto> inscrireUnUtilisateur(@RequestBody UtilisateurRequestDto utilisateurRequestDto) {
        UtilisateurResponseDto utilisateurResponseDto = utilisateurService.inscrireUnUtilisateur(utilisateurRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateurResponseDto);
    }

    @PostMapping("/connexion")
    public ResponseEntity<UtilisateurResponseDto> connecterUnUtilisateur(@RequestBody UtilisateurRequestDto utilisateurRequestDto) {
        UtilisateurResponseDto utilisateurResponseDto = utilisateurService.connecterUnUtilisateur(
                utilisateurRequestDto.getEmail(),
                utilisateurRequestDto.getPassword()
        );
        return ResponseEntity.ok(utilisateurResponseDto);
    }
}
