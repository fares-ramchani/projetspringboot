package com.example.cyberParc.coucheWeb;

import com.example.cyberParc.coucheDTO.ClientRequestDto;
import com.example.cyberParc.coucheService.serviceClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "Client")
@AllArgsConstructor
@CrossOrigin("*")
public class ClientController {
    private serviceClient ClientService;
    @PostMapping(path = "/AddClient")
    public void ajouterClient(@RequestBody ClientRequestDto ClientRequestDto)
    {
        ClientService.ajouterClient(ClientRequestDto);
    }
}
