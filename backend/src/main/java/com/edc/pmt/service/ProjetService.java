package com.edc.pmt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.edc.pmt.dto.request.ProjetRequestDto;
import com.edc.pmt.dto.response.ProjetResponseDto;
import com.edc.pmt.entity.MembreProjet;
import com.edc.pmt.entity.Projet;
import com.edc.pmt.entity.Utilisateur;
import com.edc.pmt.enums.Role;
import com.edc.pmt.mapper.ProjetMapper;
import com.edc.pmt.repository.MembreProjetRepository;
import com.edc.pmt.repository.ProjetRepository;
import com.edc.pmt.repository.UtilisateurRepository;

@Service
public class ProjetService {
    private final ProjetMapper projetMapper;
    private final ProjetRepository projetRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final MembreProjetRepository membreProjetRepository;

    public ProjetService(
            ProjetMapper projetMapper,
            ProjetRepository projetRepository,
            UtilisateurRepository utilisateurRepository,
            MembreProjetRepository membreProjetRepository) {
        this.projetMapper = projetMapper;
        this.projetRepository = projetRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.membreProjetRepository = membreProjetRepository;
    }

    public ProjetResponseDto creerUnProjet(ProjetRequestDto projetRequestDto, Long administrateurId) {

        Utilisateur administrateur = utilisateurRepository.findById(administrateurId)
                .orElseThrow(() -> new RuntimeException(
                        "Aucun utilisateur trouvé avec l'identifiant : " + administrateurId));

        Projet projet = projetMapper.convertirRequestDtoEnProjet(projetRequestDto, administrateur);
        Projet projetSauvegarde = projetRepository.save(projet);

        MembreProjet membreAdministrateur = new MembreProjet();
        membreAdministrateur.setUtilisateur(administrateur);
        membreAdministrateur.setProjet(projetSauvegarde);
        membreAdministrateur.setRole(Role.ADMINISTRATEUR);
        membreProjetRepository.save(membreAdministrateur);

        return projetMapper.convertirProjetEnResponseDto(projetSauvegarde);
    }

    public List<ProjetResponseDto> recupererLesProjetsDeUnUtilisateur(Long utilisateurId) {
        List<Projet> listeProjets = projetRepository.findLesProjetsDeUnUtilisateur(utilisateurId);
        return listeProjets.stream()
                .map(projetMapper::convertirProjetEnResponseDto)
                .collect(Collectors.toList());
    }

    public List<ProjetResponseDto> recupererTousLesProjets() {
        List<Projet> listeProjets = projetRepository.findAll();
        return listeProjets.stream()
                .map(projetMapper::convertirProjetEnResponseDto)
                .collect(Collectors.toList());
    }

    public ProjetResponseDto recupererUnProjet(Long projetId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet introuvable : " + projetId));
        return projetMapper.convertirProjetEnResponseDto(projet);
    }

    public ProjetResponseDto mettreAJourUnProjet(Long projetId, ProjetRequestDto projetRequestDto) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet introuvable : " + projetId));

        projet.setNom(projetRequestDto.getNom());
        projet.setDescription(projetRequestDto.getDescription());
        projet.setDateDeDebut(projetRequestDto.getDateDeDebut());

        Projet projetMisAJour = projetRepository.save(projet);
        return projetMapper.convertirProjetEnResponseDto(projetMisAJour);
    }

}
