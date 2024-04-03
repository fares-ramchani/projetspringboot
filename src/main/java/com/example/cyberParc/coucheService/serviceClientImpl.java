package com.example.cyberParc.coucheService;

import com.example.cyberParc.ENTITY.client;
import com.example.cyberParc.coucheDAO.clinetRepository;
import com.example.cyberParc.coucheDTO.ClientRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class serviceClientImpl implements serviceClient {
    private clinetRepository ClientRepository;
    private org.springframework.security.provisioning.JdbcUserDetailsManager JdbcUserDetailsManager;
    private org.springframework.security.crypto.password.PasswordEncoder PasswordEncoder;
    @Override
    public void ajouterClient(ClientRequestDto ClientRequestDto) {

        client client=new client();
        client.setNom(ClientRequestDto.getUsername());
        client.setPrenom(ClientRequestDto.getPrenom());
        client.setMail(ClientRequestDto.getEmail());
        JdbcUserDetailsManager.createUser(User.withUsername(ClientRequestDto.getEmail()).password(PasswordEncoder.encode(ClientRequestDto.getPassword())).authorities("CLIENT").build());
        ClientRepository.save(client);
    }
}
