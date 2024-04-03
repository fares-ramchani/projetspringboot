package com.example.cyberParc.ENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CV")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CVdemandeur {
    public CVdemandeur(String name,String type,byte[] picbyte,demandeur demandeur){
        this.name=name;
        this.type=type;
        this.picbyte=picbyte;
        this.demandeur=demandeur;
    }
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="type")
    private String type;
    @Column(name="picbyte",length = 50000000)
    private byte[] picbyte;
    @ManyToOne
    @JoinColumn(name = "demandeur")
    private demandeur demandeur;
}
