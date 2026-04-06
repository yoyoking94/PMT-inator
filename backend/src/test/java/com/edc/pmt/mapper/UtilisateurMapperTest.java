package com.edc.pmt.mapper;

import com.edc.pmt.dto.request.UtilisateurRequestDto;
import com.edc.pmt.dto.response.UtilisateurResponseDto;
import com.edc.pmt.entity.Utilisateur;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilisateurMapperTest {

    private final UtilisateurMapper utilisateurMapper = new UtilisateurMapper();

    @Test
    void convertirRequeteDtoEnUser_doitRetournerUnUtilisateurAvecLesbonnesValeurs() {
        UtilisateurRequestDto requestDto = new UtilisateurRequestDto();
        requestDto.setUsername("Jean Dupont");
        requestDto.setEmail("jean.dupont@email.com");
        requestDto.setPassword("motdepasse123");

        Utilisateur utilisateur = utilisateurMapper.convertirRequeteDtoEnUtilisateur(requestDto);

        assertThat(utilisateur).isNotNull();
        assertThat(utilisateur.getUsername()).isEqualTo("Jean Dupont");
        assertThat(utilisateur.getEmail()).isEqualTo("jean.dupont@email.com");
        assertThat(utilisateur.getPassword()).isEqualTo("motdepasse123");
    }

    @Test
    void convertirUserEnResponseDto_doitRetournerUnResponseDtoAvecLesBonnesValeurs() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setUsername("Jean Dupont");
        utilisateur.setEmail("jean.dupont@email.com");
        utilisateur.setDateDeCreation(LocalDateTime.now());

        UtilisateurResponseDto responseDto = utilisateurMapper.convertirUtilisateurEnResponseDto(utilisateur);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getUsername()).isEqualTo("Jean Dupont");
        assertThat(responseDto.getEmail()).isEqualTo("jean.dupont@email.com");
    }
}