package com.example.cyberParc.coucheDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientRequestDto {
    private String password;
    private String username;
    private String email;
    private String prenom;



}
