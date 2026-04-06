package com.edc.pmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edc.pmt.entity.HistoriqueTache;

@Repository
public interface HistoriqueTacheRepository extends JpaRepository<HistoriqueTache, Long> {
    List<HistoriqueTache> findByTacheIdOrderByDateDeModificationDesc(Long tache_id);    
}
