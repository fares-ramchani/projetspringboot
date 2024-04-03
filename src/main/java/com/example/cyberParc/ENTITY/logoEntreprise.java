package com.example.cyberParc.ENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "logo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class logoEntreprise {

        public logoEntreprise(String name,String type,byte[] picbyte,demande demande,entreprise entreprise){
            this.name=name;
            this.type=type;
            this.picbyte=picbyte;
            this.demande=demande;
            this.entreprise=entreprise;
        }
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        @Column(name="id")
        private Long id;
        @Column(name="name")
        private String name;
        @Column(name="type")
        private String type;
        @Column(name="picbyte",length = 5000000)
        private byte[] picbyte;
        @ManyToOne
        @JoinColumn(name = "demande")
        private demande demande;
        @ManyToOne
        @JoinColumn(name = "entreprise")
        private entreprise entreprise;
    }

