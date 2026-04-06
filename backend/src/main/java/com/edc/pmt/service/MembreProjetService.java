package com.edc.pmt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.edc.pmt.dto.request.MembreProjetRequestDto;
import com.edc.pmt.dto.response.MembreProjetResponseDto;
import com.edc.pmt.entity.MembreProjet;
import com.edc.pmt.entity.Projet;
import com.edc.pmt.entity.Utilisateur;
import com.edc.pmt.enums.Role;
import com.edc.pmt.mapper.MembreProjetMapper;
import com.edc.pmt.repository.MembreProjetRepository;
import com.edc.pmt.repository.ProjetRepository;
import com.edc.pmt.repository.UtilisateurRepository;

@Service
public class MembreProjetService {

        private final MembreProjetMapper membreProjetMapper;
        private final MembreProjetRepository membreProjetRepository;
        private final UtilisateurRepository utilisateurRepository;
        private final ProjetRepository projetRepository;

        public MembreProjetService(MembreProjetMapper membreProjetMapper,
                        MembreProjetRepository membreProjetRepository,
                        UtilisateurRepository utilisateurRepository,
                        ProjetRepository projetRepository) {
                this.membreProjetMapper = membreProjetMapper;
                this.membreProjetRepository = membreProjetRepository;
                this.utilisateurRepository = utilisateurRepository;
                this.projetRepository = projetRepository;
        }

        public MembreProjetResponseDto inviterUnMembre(Long projetId, Long administrateurId,
                        MembreProjetRequestDto membreProjetRequestDto) {

                // 1. Vérifier que l'administrateur est bien ADMINISTRATEUR du projet
                MembreProjet membreAdministrateur = membreProjetRepository
                                .findByProjetIdAndUtilisateurId(projetId, administrateurId)
                                .orElseThrow(() -> new RuntimeException("Vous n'êtes pas membre de ce projet."));

                if (membreAdministrateur.getRole() != Role.ADMINISTRATEUR) {
                        throw new RuntimeException("Seul un administrateur peut inviter des membres.");
                }

                // 2. Chercher l'utilisateur à inviter
                Utilisateur utilisateurAInviter = utilisateurRepository
                                .findByEmail(membreProjetRequestDto.getEmail())
                                .orElseThrow(() -> new RuntimeException(
                                                "Aucun utilisateur trouvé avec l'email : "
                                                                + membreProjetRequestDto.getEmail()));

                // 3. Vérifier le doublon ← AVANT findById
                if (membreProjetRepository.existsByUtilisateurIdAndProjetId(
                                utilisateurAInviter.getId(), projetId)) {
                        throw new RuntimeException("Cet utilisateur est déjà membre du projet.");
                }

                // 4. Chercher le projet
                Projet projet = projetRepository.findById(projetId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Aucun projet trouvé avec l'identifiant : " + projetId));

                // 5. Créer et sauvegarder le nouveau membre
                MembreProjet nouveauMembre = membreProjetMapper
                                .convertirRequestDtoEnMembreProjet(membreProjetRequestDto);
                nouveauMembre.setUtilisateur(utilisateurAInviter);
                nouveauMembre.setProjet(projet);

                MembreProjet membreSauvegarde = membreProjetRepository.save(nouveauMembre);
                return membreProjetMapper.convertirMembreProjetEnResponseDto(membreSauvegarde);
        }

        public List<MembreProjetResponseDto> recupererLesMembresDeUnProjet(Long projetId) {
                List<MembreProjet> listeMembres = membreProjetRepository.findByProjetId(projetId);
                return listeMembres.stream()
                                .map(membreProjetMapper::convertirMembreProjetEnResponseDto)
                                .collect(Collectors.toList());
        }
}