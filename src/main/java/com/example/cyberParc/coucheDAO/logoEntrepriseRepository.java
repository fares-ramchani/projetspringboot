package com.example.cyberParc.coucheDAO;

import com.example.cyberParc.ENTITY.demande;
import com.example.cyberParc.ENTITY.entreprise;
import com.example.cyberParc.ENTITY.logoEntreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface logoEntrepriseRepository extends JpaRepository<logoEntreprise,Long> {
    logoEntreprise findByDemande_Id(Long id);

    long deleteByDemande(demande demande);

    long deleteByEntreprise(entreprise entreprise);

}
