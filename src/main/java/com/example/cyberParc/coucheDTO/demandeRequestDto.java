package com.example.cyberParc.coucheDTO;

import com.example.cyberParc.ENTITY.demandeur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class demandeRequestDto {
    private String nom;
    private String prenom;
    private String mail;
    private String tel;
    private String cin;
    private String nomEntreprise;
    private String ideeProjet;
    private String mailEntreprise;
    private String service;
}
