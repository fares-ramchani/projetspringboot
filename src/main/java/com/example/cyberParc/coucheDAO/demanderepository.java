package com.example.cyberParc.coucheDAO;

import com.example.cyberParc.ENTITY.demande;
import com.example.cyberParc.Enum.EnumDemande;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Collection;
import java.util.List;

public interface demanderepository extends JpaRepository<demande,Long> {

}
