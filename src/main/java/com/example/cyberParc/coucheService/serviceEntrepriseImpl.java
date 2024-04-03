package com.example.cyberParc.coucheService;

import com.example.cyberParc.ENTITY.demande;
import com.example.cyberParc.ENTITY.entreprise;
import com.example.cyberParc.ENTITY.logoEntreprise;
import com.example.cyberParc.coucheDAO.entrepriseRepository;
import com.example.cyberParc.coucheDAO.logoEntrepriseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@Transactional
@AllArgsConstructor
public class serviceEntrepriseImpl implements serviceEntreprise {
    private entrepriseRepository entrepriseRepository;
    private logoEntrepriseRepository logoEntrepriseRepositore;
    @Override
    public List<entreprise> getEntreprises() {
        List<entreprise> entreprise=entrepriseRepository.findAll();

        return entreprise;
    }
    @Override
    public void AjoutEntreprise(String entrepriseRequestDTO, MultipartFile File) throws IOException {
        entreprise entreprise=new ObjectMapper().readValue(entrepriseRequestDTO,entreprise.class);
        logoEntreprise logoo=new logoEntreprise(File.getOriginalFilename(),File.getContentType(), compressBytes(File.getBytes()),null,entreprise);
        entrepriseRepository.save(entreprise);
        logoEntrepriseRepositore.save(logoo);
    }
    @Override
    public void SupprimerEntreprise(Long id)
    {
        entreprise entreprise=entrepriseRepository.findById(id).get();
        logoEntrepriseRepositore.deleteByEntreprise(entreprise);
        entrepriseRepository.deleteById(id);
    }
    @Override
    public void ModifierEntreprise(Long id,String entrepriseRequestDTO, MultipartFile File)throws IOException
    {
        entreprise entreprise=entrepriseRepository.findById(id).get();
        entreprise entrepriseModifier=new ObjectMapper().readValue(entrepriseRequestDTO,entreprise.class);

        entrepriseModifier.setId(id);
        if(entrepriseModifier.getNomEntreprise().equals(""))
        {
            entrepriseModifier.setNomEntreprise(entreprise.getNomEntreprise());
        }
        if(entrepriseModifier.getMailEntreprise().equals(""))
        {
            entrepriseModifier.setMailEntreprise(entreprise.getMailEntreprise());
        }
        if(entrepriseModifier.getService().equals(""))
        {
            entrepriseModifier.setService(entreprise.getService());
        }
        if(entrepriseModifier.getGerant().equals(""))
        {
            entrepriseModifier.setGerant(entreprise.getGerant());
        }
        if(File==null)
        {
            entrepriseModifier.setLogoEntreprise(entreprise.getLogoEntreprise());
        }else{
            logoEntreprise logoo=new logoEntreprise(File.getOriginalFilename(),File.getContentType(), compressBytes(File.getBytes()),null,entreprise);
            logoEntrepriseRepositore.deleteByEntreprise(entreprise);
            logoEntrepriseRepositore.save(logoo);
        }
        entrepriseRepository.save(entrepriseModifier);
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
}
