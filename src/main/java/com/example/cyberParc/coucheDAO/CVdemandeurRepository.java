package com.example.cyberParc.coucheDAO;

import com.example.cyberParc.ENTITY.CVdemandeur;
import com.example.cyberParc.ENTITY.demandeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CVdemandeurRepository extends JpaRepository<CVdemandeur,Long> {
    CVdemandeur findByDemandeur_Id(Long id);

    long deleteByDemandeur(demandeur demandeur);
}
