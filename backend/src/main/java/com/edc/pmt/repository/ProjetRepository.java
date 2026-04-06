package com.edc.pmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edc.pmt.entity.Projet;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    List<Projet> findByAdministrateurId(Long administrateurId);
    Optional<Projet> findByNom(String nom);

    @Query("SELECT mp.projet FROM MembreProjet mp WHERE mp.utilisateur.id = :utilisateurId")
    List<Projet> findLesProjetsDeUnUtilisateur(@Param("utilisateurId") Long utilisateurId);
}
