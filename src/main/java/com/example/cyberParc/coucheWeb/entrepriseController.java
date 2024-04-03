package com.example.cyberParc.coucheWeb;

import com.example.cyberParc.ENTITY.entreprise;
import com.example.cyberParc.coucheService.serviceEntreprise;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "entreprise")
@AllArgsConstructor
@CrossOrigin("*")
public class entrepriseController {
    private serviceEntreprise serviceEntreprise;
    @GetMapping(path = "/getEntreprises")
    public List<entreprise> getEntreprises()
    {
        return serviceEntreprise.getEntreprises();
    }
    @PostMapping(path="/AjoutEntreprise")
    public void AjoutEntreprise(@RequestParam("entrepriseRequestDTO") String entrepriseRequestDTO,
                                                       @RequestParam("logo") MultipartFile logo) throws IOException {
        System.out.println(entrepriseRequestDTO);
        serviceEntreprise.AjoutEntreprise(entrepriseRequestDTO,logo);

    }
    @PutMapping(path="/ModifierEntreprise")
    public void ModifierEntreprise(@RequestParam("entrepriseRequestDto") String entrepriseRequestDTO,
                                @RequestParam("logo") MultipartFile logo,
                                @RequestParam("idEntreprise") String id) throws IOException {
        if(logo==null)
        {
            serviceEntreprise.ModifierEntreprise(Long.parseLong(id),entrepriseRequestDTO,null);
        }
        else
        {
            serviceEntreprise.ModifierEntreprise(Long.parseLong(id),entrepriseRequestDTO,logo);
        }

    }
    @PutMapping(path="/ModifierEntreprise2")
    public void ModifierEntreprise2(@RequestParam("entrepriseRequestDto") String entrepriseRequestDTO,
                                   @RequestParam("idEntreprise") String id) throws IOException {


            serviceEntreprise.ModifierEntreprise(Long.parseLong(id),entrepriseRequestDTO,null);


    }
    @DeleteMapping(path="/SupprimerEntreprise")
    public void SupprimerEntreprise(@RequestParam Long id)
    {
        serviceEntreprise.SupprimerEntreprise(id);
    }
}
