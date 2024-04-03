package com.example.cyberParc.ENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Entity
@NoArgsConstructor
@Data
public class demandeur {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String mail;
    private String tel;
    private String cin;
    @ManyToOne
    @JoinColumn(name = "demande")
    private demande demande;
    @Transient
    @OneToMany(mappedBy = "demandeur",fetch = FetchType.LAZY)
    private List<DiplomeDemandeur> diplome;
    @Transient
    @OneToMany(mappedBy = "demandeur",fetch = FetchType.LAZY)
    private List<CVdemandeur> cv;
}
