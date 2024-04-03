package com.example.cyberParc.coucheService;

import com.example.cyberParc.ENTITY.*;
import com.example.cyberParc.Enum.EnumDemande;
import com.example.cyberParc.coucheDAO.*;
import com.example.cyberParc.coucheDTO.demandeRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static com.example.cyberParc.Enum.EnumDemande.EnCoursDeTraitement;
import static com.example.cyberParc.Enum.EnumDemande.confermer;

@Service
@Transactional
@NoArgsConstructor
public class serviceDemandeImpl implements serviceDemande {
    @Autowired
private demandeurrepository demandeurrepository;
@Autowired
private demanderepository demanderepository;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
private diplomeDemandeurRepository diplomeDemandeurRepository;
    @Autowired
private CVdemandeurRepository CVdemandeurRepository;
    @Autowired
private SendMailMessage sendMailMessage;
    @Autowired
private entrepriseRepository entrepriseRepository;
    @Autowired
private logoEntrepriseRepository logoEntrepriseRepository;
private Long id;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void ajouterDemande(String demandeRequestDto, MultipartFile File,String demandeurRequestDto,MultipartFile File2,MultipartFile logo) throws IOException {
        demande demande=new ObjectMapper().readValue(demandeRequestDto,demande.class);
        demandeur demandeur=new ObjectMapper().readValue(demandeurRequestDto,demandeur.class);
        demande.setStatue(EnCoursDeTraitement);
        demandeur.setDemande(demande);
        DiplomeDemandeur img=new DiplomeDemandeur(File.getOriginalFilename(),File.getContentType(), compressBytes(File.getBytes()),demandeur);
        CVdemandeur img2=new CVdemandeur(File2.getOriginalFilename(),File2.getContentType(), compressBytes(File2.getBytes()),demandeur);
        logoEntreprise logoo=new logoEntreprise(logo.getOriginalFilename(),logo.getContentType(), compressBytes(logo.getBytes()),demande,null);
        diplomeDemandeurRepository.save(img);
        CVdemandeurRepository.save(img2);
        logoEntrepriseRepository.save(logoo);
        demandeurrepository.save(demandeur);
        demanderepository.save(demande);

    }
    @Override
    public void getid(Long id){
      this.id=id;
    }
    @Transactional
    public void deleteDemandeApresDisJour(Long id){
        demande demande=demanderepository.findById(id).get();
        demandeur demandeur=demandeurrepository.findByDemande_Id(id);
        diplomeDemandeurRepository.deleteByDemandeur(demandeur);
        CVdemandeurRepository.deleteByDemandeur(demandeur);
        logoEntrepriseRepository.deleteByDemande(demande);
        demandeurrepository.deleteByDemande(demande);
        demanderepository.deleteById(id);
        this.id=null;
    }
    @Override
    public void planifierTacheDifferee(Long id) {
        executorService.schedule(() -> transactionTemplate.execute(status -> {
            deleteDemandeApresDisJour(id);
            return null;
        }), 10, TimeUnit.DAYS);
    }
    @Override
    public List<demande> listeDemande()
    {
        List<demande> demandeList=new ArrayList<>();
        List<demande>  demande=demanderepository.findAll();
        for (int i=0;i<demande.size();i++)
        {
            if (demande.get(i).getStatue().toString().equals("EnCoursDeTraitement")){
                demandeList.add(demande.get(i));
            }
        }
        return demandeList;
    }
    @Override
    public List<demande> listeDemandeAcepter()
    {
        List<demande> demandeList=new ArrayList<>();
        List<demande>  demande=demanderepository.findAll();
        for (int i=0;i<demande.size();i++)
        {
            if (demande.get(i).getStatue().toString().equals("Aceepter")){
                demandeList.add(demande.get(i));
            }
        }
        return demandeList;
    }
    @Override
    public List<demandeur> listeDemandeur()
    {
        List<demandeur> demandeurs=demandeurrepository.findAll();
        return  demandeurs;
    }
    @Override
    public List<CVdemandeur> ListeCv()
    {
        List<CVdemandeur> cv1= CVdemandeurRepository.findAll();
        List<CVdemandeur> cv2=new ArrayList<>();
        for(int i=0;i<cv1.size();i++)
        {
            cv2.add(new CVdemandeur(cv1.get(i).getName(),cv1.get(i).getType(),decompressBytes(cv1.get(i).getPicbyte()),cv1.get(i).getDemandeur()));
        }
        return cv2;
    }
    @Override
    public CVdemandeur getCv(Long id){
        CVdemandeur cv1= CVdemandeurRepository.findByDemandeur_Id(id);
        CVdemandeur cv2=new CVdemandeur();
        cv2=(new CVdemandeur(cv1.getName(),cv1.getType(),decompressBytes(cv1.getPicbyte()),cv1.getDemandeur()));
        return cv2;
    }
    @Override
    public DiplomeDemandeur getDeplome(Long id)
    {
        DiplomeDemandeur diplome1=diplomeDemandeurRepository.findByDemandeur_Id(id);
        DiplomeDemandeur diplome2= new DiplomeDemandeur();
        diplome2=(new DiplomeDemandeur(diplome1.getName(),diplome1.getType(),decompressBytes(diplome1.getPicbyte()),diplome1.getDemandeur()));
        return diplome2;
    }
    @Override
    public List<DiplomeDemandeur> ListeDeplomes()
    {
        List<DiplomeDemandeur> deplome1= diplomeDemandeurRepository.findAll();
        List<DiplomeDemandeur> deplome2=new ArrayList<>();
        for(int i=0;i<deplome1.size();i++)
        {
            deplome2.add(new DiplomeDemandeur(deplome1.get(i).getName(),deplome1.get(i).getType(),decompressBytes(deplome1.get(i).getPicbyte()),deplome1.get(i).getDemandeur()));
        }
        return deplome2;
    }
    @Override
    public List<logoEntreprise> Listelogo(){
        List<logoEntreprise> logo1= logoEntrepriseRepository.findAll();
        List<logoEntreprise> logo2=new ArrayList<>();
        for(int i=0;i<logo1.size();i++)
        {
            logo2.add(new logoEntreprise(logo1.get(i).getName(),logo1.get(i).getType(),decompressBytes(logo1.get(i).getPicbyte()),logo1.get(i).getDemande(),logo1.get(i).getEntreprise()));
        }
        return logo2;
    }
    @Override
    public void accepterDemande(Long id) throws MessagingException {
        demande demande=demanderepository.findById(id).get();
        demandeur demandeur=demandeurrepository.findByDemande_Id(id);
        demande.setStatue(EnumDemande.Aceepter);
        sendMailMessage.sendMailToClient(demandeur.getMail(),"votre demande a ete accepter"+demandeur.getNom()+" "+demandeur.getPrenom(),"Confirmation de demande");
        demanderepository.save(demande);
    }
    @Override
    public void confermerDemande(Long id){
        demande demande=demanderepository.findById(id).get();
        demandeur demandeur=demandeurrepository.findByDemande_Id(id);
        logoEntreprise logoEntreprise=logoEntrepriseRepository.findByDemande_Id(id);
        demande.setStatue(EnumDemande.confermer);
        entreprise entreprise=new entreprise();
        entreprise.setService(demande.getService());
        entreprise.setMailEntreprise(demande.getMailEntreprise());
        entreprise.setNomEntreprise(demande.getNomEntreprise());
        entreprise.setGerant(demandeur.getNom()+" "+demandeur.getPrenom());
        logoEntreprise.setEntreprise(entreprise);
        entrepriseRepository.save(entreprise);
        logoEntrepriseRepository.save(logoEntreprise);
    }
    @Override
    public void refuserDemande(Long id) throws MessagingException
    {
        demande demande=demanderepository.findById(id).get();
        demandeur demandeur=demandeurrepository.findByDemande_Id(id);
        demande.setStatue(EnumDemande.Refuser);
        sendMailMessage.sendMailToClient(demandeur.getMail(),"votre demande a été refuser "+demandeur.getNom()+" "+demandeur.getPrenom(),"Confirmation de demande");
        demanderepository.save(demande);
    }
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
