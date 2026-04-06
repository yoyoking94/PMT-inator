package com.edc.pmt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.edc.pmt.dto.request.UtilisateurRequestDto;
import com.edc.pmt.dto.response.UtilisateurResponseDto;
import com.edc.pmt.entity.Utilisateur;
import com.edc.pmt.mapper.UtilisateurMapper;
import com.edc.pmt.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    private final UtilisateurMapper utilisateurMapper;
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurMapper utilisateurMapper, UtilisateurRepository utilisateurRepository) {
        this.utilisateurMapper = utilisateurMapper;
        this.utilisateurRepository = utilisateurRepository;
    }

    public UtilisateurResponseDto inscrireUnUtilisateur(UtilisateurRequestDto utilisateurRequestDto) {
        Utilisateur utilisateur = utilisateurMapper.convertirRequeteDtoEnUtilisateur(utilisateurRequestDto);
        Utilisateur userSauvegarde = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.convertirUtilisateurEnResponseDto(userSauvegarde);
    }

    public UtilisateurResponseDto connecterUnUtilisateur(String email, String motDePasse) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Aucun utilisateur trouvé avec cet email : " + email));

        if (!utilisateur.getPassword().equals(motDePasse)) {
            throw new RuntimeException("Le mot de passe est incorrect.");
        }

        return utilisateurMapper.convertirUtilisateurEnResponseDto(utilisateur);
    }

    public List<UtilisateurResponseDto> recupererTousLesUtilisateurs() {
        List<Utilisateur> listeUtilisateurs = utilisateurRepository.findAll();
        return listeUtilisateurs.stream()
                .map(utilisateurMapper::convertirUtilisateurEnResponseDto)
                .collect(Collectors.toList());
    }

    public UtilisateurResponseDto recupererUnUtilisateur(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun utilisateur trouvé avec l'identifiant : " + id));
        return utilisateurMapper.convertirUtilisateurEnResponseDto(utilisateur);
    }
}
