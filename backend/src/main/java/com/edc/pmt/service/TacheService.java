package com.edc.pmt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.edc.pmt.dto.request.TacheRequestDto;
import com.edc.pmt.dto.response.TacheResponseDto;
import com.edc.pmt.entity.MembreProjet;
import com.edc.pmt.entity.Projet;
import com.edc.pmt.entity.Tache;
import com.edc.pmt.entity.Utilisateur;
import com.edc.pmt.enums.Role;
import com.edc.pmt.mapper.TacheMapper;
import com.edc.pmt.repository.MembreProjetRepository;
import com.edc.pmt.repository.ProjetRepository;
import com.edc.pmt.repository.TacheRepository;
import com.edc.pmt.repository.UtilisateurRepository;

@Service
public class TacheService {
        private final TacheMapper tacheMapper;
        private final TacheRepository tacheRepository;
        private final UtilisateurRepository utilisateurRepository;
        private final ProjetRepository projetRepository;
        private final MembreProjetRepository membreProjetRepository;
        private final NotificationEmailService notificationEmailService;

        public TacheService(TacheMapper tacheMapper, TacheRepository tacheRepository,
                        UtilisateurRepository utilisateurRepository,
                        ProjetRepository projetRepository, MembreProjetRepository membreProjetRepository,
                        NotificationEmailService notificationEmailService) {
                this.tacheMapper = tacheMapper;
                this.tacheRepository = tacheRepository;
                this.utilisateurRepository = utilisateurRepository;
                this.projetRepository = projetRepository;
                this.membreProjetRepository = membreProjetRepository;
                this.notificationEmailService = notificationEmailService;
        }

        public TacheResponseDto creerUneTache(TacheRequestDto tacheRequestDto, Long projetId,
                        Long membreAssigneId, Long createurId) {

                // On vérifie le rôle du CRÉATEUR, pas de la personne assignée
                MembreProjet membreCreateur = membreProjetRepository
                                .findByProjetIdAndUtilisateurId(projetId, createurId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Vous n'êtes pas membre de ce projet."));

                if (membreCreateur.getRole() == Role.OBSERVATEUR) {
                        throw new RuntimeException("Les observateurs ne peuvent pas créer de tâches.");
                }

                Utilisateur membreAssigne = utilisateurRepository.findById(membreAssigneId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Aucun utilisateur trouvé avec l'identifiant : " + membreAssigneId));

                Projet projet = projetRepository.findById(projetId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Aucun projet trouvé avec l'identifiant : " + projetId));

                Tache tache = tacheMapper.convertirRequestDtoEnTache(tacheRequestDto, projet, membreAssigne);
                Tache tacheSauvegarde = tacheRepository.save(tache);

                try {
                        notificationEmailService.envoyerEmailAssignation(
                                        membreAssigne.getEmail(),
                                        tacheSauvegarde.getNom(),
                                        projet.getNom());
                } catch (Exception erreurEnvoyerEmail) {
                        System.err.println("Avertissement : l'email n'a pas pu être envoyé. "
                                        + erreurEnvoyerEmail.getMessage());
                }

                return tacheMapper.convertirTacheEnResponseDto(tacheSauvegarde);
        }

        public List<TacheResponseDto> recupererLesTachesDeUnProjet(Long projetId) {
                List<Tache> listeTaches = tacheRepository.findByProjetId(projetId);
                return listeTaches.stream()
                                .map(tacheMapper::convertirTacheEnResponseDto)
                                .collect(Collectors.toList());
        }

        public TacheResponseDto mettreAJourUneTache(TacheRequestDto tacheRequestDto, Long tacheId) {
                Tache tache = tacheRepository.findById(tacheId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Tâche introuvable avec l'identifiant : " + tacheId));

                // Vérifie que l'utilisateur est ADMINISTRATEUR ou MEMBRE du projet
                MembreProjet membreModificateur = membreProjetRepository
                                .findByProjetIdAndUtilisateurId(tache.getProjet().getId(),
                                                tacheRequestDto.getModificateurId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Vous n'êtes pas membre de ce projet."));

                if (membreModificateur.getRole() == Role.OBSERVATEUR) {
                        throw new RuntimeException(
                                        "Les observateurs ne peuvent pas modifier des tâches.");
                }

                tache.setNom(tacheRequestDto.getNom());
                tache.setDescription(tacheRequestDto.getDescription());
                tache.setDateEcheant(tacheRequestDto.getDateEcheant());
                tache.setPriorite(tacheRequestDto.getPriorite());

                Tache tacheMaj = tacheRepository.save(tache);
                return tacheMapper.convertirTacheEnResponseDto(tacheMaj);
        }

        public TacheResponseDto recupererUneTache(Long tacheId) {
                Tache tache = tacheRepository.findById(tacheId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Aucune tâche trouvée avec l'identifiant : " + tacheId));
                return tacheMapper.convertirTacheEnResponseDto(tache);
        }

}
