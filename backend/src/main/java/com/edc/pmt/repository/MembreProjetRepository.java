package com.edc.pmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edc.pmt.entity.MembreProjet;

@Repository
public interface MembreProjetRepository extends JpaRepository<MembreProjet, Long> {
    List<MembreProjet> findByProjetId(Long projetId);

    Boolean existsByUtilisateurIdAndProjetId(Long utilisateurId, Long projetId);
    Optional<MembreProjet> findByProjetIdAndUtilisateurId(Long projetId, Long utilisateurId);
}
