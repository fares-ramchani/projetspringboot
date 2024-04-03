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
public class entreprise {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nomEntreprise;
    private String gerant;
    private String mailEntreprise;
    private String service;
    private String siteEntreprise;
    @Transient
    @OneToMany(mappedBy = "entreprise",fetch = FetchType.LAZY)
    private List<logoEntreprise> logoEntreprise;
}
