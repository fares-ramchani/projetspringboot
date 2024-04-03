package com.example.cyberParc.ENTITY;

import com.example.cyberParc.Enum.EnumDemande;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Entity
@NoArgsConstructor
@Data
public class demande {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nomEntreprise;
    private String ideeProjet;
    private String mailEntreprise;
    private String service;
    @Enumerated(EnumType.STRING)
    private EnumDemande Statue;
    @Transient
    @OneToMany(mappedBy = "demande",fetch = FetchType.LAZY)
    private List<demandeur> demandeur;
    @Transient
    @OneToMany(mappedBy = "demande",fetch = FetchType.LAZY)
    private List<logoEntreprise> logoEntreprise;
}
