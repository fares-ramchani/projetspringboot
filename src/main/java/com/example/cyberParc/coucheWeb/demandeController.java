package com.example.cyberParc.coucheWeb;
import com.example.cyberParc.ENTITY.*;
import com.example.cyberParc.coucheService.serviceDemande;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "Demande")
@AllArgsConstructor
@CrossOrigin("*")
public class demandeController {
    private serviceDemande serviceDemande;
    @PostMapping(path = "/ajouterDemande")
    public ResponseEntity.BodyBuilder ajouterClient(@RequestParam("demandeRequestDto") String demandeRequestDto,
                                                    @RequestParam("demandeurRequestDto") String demandeurRequestDto,
                                                    @RequestParam("diplomeFile") MultipartFile file,
                                                    @RequestParam("CVFile") MultipartFile file2,
                                                    @RequestParam("logo") MultipartFile logo) throws IOException {
        serviceDemande.ajouterDemande(demandeRequestDto,file,demandeurRequestDto,file2,logo);
        return ResponseEntity.status(HttpStatus.OK);
    }
    @GetMapping(path = "/listeDemande")
    public List<demande> listeDemande(){
        return serviceDemande.listeDemande();
    }
    @GetMapping(path = "/listeDemandeAccepter")
    public List<demande> listeDemandeAcepter(){
        return serviceDemande.listeDemandeAcepter();
    }
    @GetMapping(path = "/listeDemandeur")
    public List<demandeur> listeDemandeur(){
        return serviceDemande.listeDemandeur();
    }
    @GetMapping(path = "/GetCvs")
    public List<CVdemandeur> Getcvs()throws IOException
    {
        return serviceDemande.ListeCv();
    }
    @GetMapping(path = "/Getlogos")
    public List<logoEntreprise> Getlogos()throws IOException
    {

        return serviceDemande.Listelogo();
    }
    @GetMapping(path = "/GetCv")
    public CVdemandeur Getcv(@RequestParam Long id)throws IOException
    {
        return serviceDemande.getCv(id);
    }
    @GetMapping(path="/GetDeplome")
    public DiplomeDemandeur GetDeplome(@RequestParam Long id)throws IOException
    {
        return serviceDemande.getDeplome(id);
    }
    @GetMapping(path="/GetDeplomes")
    public List<DiplomeDemandeur> ListeDeplomes()
    {
        return serviceDemande.ListeDeplomes();
    }
    @PutMapping(path = "/accepterdemande")
    public void accepterdemande(@RequestParam("id")  Long id) throws MessagingException {
        serviceDemande.accepterDemande(id);
        serviceDemande.getid(id);
        serviceDemande.planifierTacheDifferee(id);
    }
    @PutMapping(path = "/confermerdemande")
    public void confermerdemande(@RequestParam("id")  Long id)  {
        serviceDemande.confermerDemande(id);
    }
    @PutMapping(path="/refuserDemande")
    public void refuserDemande (@RequestParam("id")  Long id) throws MessagingException
    {
        serviceDemande.refuserDemande(id);
    }

}
