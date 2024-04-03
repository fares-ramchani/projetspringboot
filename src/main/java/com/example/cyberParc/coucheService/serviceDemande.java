package com.example.cyberParc.coucheService;

import com.example.cyberParc.ENTITY.*;
import com.example.cyberParc.coucheDTO.demandeRequestDto;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface serviceDemande {
    void ajouterDemande(String demandeRequestDto, MultipartFile File,String demandeurRequestDto,MultipartFile File2,MultipartFile logo) throws IOException;
    public List<demande> listeDemande();
    public List<demande> listeDemandeAcepter();
    public List<demandeur> listeDemandeur();
    public List<CVdemandeur> ListeCv();
    public List<logoEntreprise> Listelogo();
    public CVdemandeur getCv(Long id);
    public DiplomeDemandeur getDeplome(Long id);
    public List<DiplomeDemandeur> ListeDeplomes();
    public void accepterDemande(Long id) throws MessagingException;
    public void confermerDemande(Long id);
    public void refuserDemande(Long id) throws MessagingException;
    public void getid(Long id);
    public void deleteDemandeApresDisJour(Long id);
    public void planifierTacheDifferee(Long id);
}
