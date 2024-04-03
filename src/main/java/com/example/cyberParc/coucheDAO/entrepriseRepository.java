package com.example.cyberParc.coucheDAO;

import com.example.cyberParc.ENTITY.entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface entrepriseRepository extends JpaRepository<entreprise,Long> {

}
