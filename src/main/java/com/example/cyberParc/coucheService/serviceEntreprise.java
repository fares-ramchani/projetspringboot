package com.example.cyberParc.coucheService;

import com.example.cyberParc.ENTITY.entreprise;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface serviceEntreprise {
    public List<entreprise> getEntreprises();
    public void AjoutEntreprise(String entrepriseRequestDTO, MultipartFile File) throws IOException;
    public void ModifierEntreprise(Long id,String entrepriseRequestDTO, MultipartFile File)throws IOException;
    public void SupprimerEntreprise(Long id);
}
