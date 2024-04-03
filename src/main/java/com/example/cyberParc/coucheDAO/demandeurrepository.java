package com.example.cyberParc.coucheDAO;

import com.example.cyberParc.ENTITY.demande;
import com.example.cyberParc.ENTITY.demandeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface demandeurrepository extends JpaRepository<demandeur,Long> {
    demandeur findByDemande_Id(Long id);

    long deleteByDemande(demande demande);
}
