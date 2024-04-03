package com.example.cyberParc.SECURITY;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/auth")
@AllArgsConstructor

public class securityControlleur {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JdbcUserDetailsManager jdbcUserDetailsManager;
    @Autowired
    private PasswordEncoder PasswordEncoder;

    @Autowired
    private JwtEncoder jwtEncoder;

    @GetMapping(path = "/profile")
    public Authentication authentification(Authentication authentication) {
        System.out.println(authentication);
        return authentication;
    }

    @PostMapping(path = "/login")
    public Map<String, String> login(String username, String password) {
        if (jdbcUserDetailsManager.userExists(username)) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            Instant instant = Instant.now();
            String scope = authentication.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(" "));
            JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                    .issuedAt(instant)
                    .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                    .subject(username)
                    .claim("scope", scope)
                    .build();
            JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                    JwsHeader.with(MacAlgorithm.HS512).build(),
                    jwtClaimsSet
            );
            String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
            return Map.of("acces-token",jwt);
        } else {
            String er1 = "username n'exist pas";
            return Map.of("er1", er1);
        }
    }
}
