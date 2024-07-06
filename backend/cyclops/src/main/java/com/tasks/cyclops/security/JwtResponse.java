package com.tasks.cyclops.security;

import java.time.Instant;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

@Configuration
public class JwtResponse {
    private JwtEncoder encoder;

    public JwtResponse(JwtEncoder encoder) {
        this.encoder = encoder;
    }
    
    public String getJwtToken(String name){
        return createToken(name);
    }
    private String createToken(String name){
        var claimSet=JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(Instant.now())
        .expiresAt(Instant.now().plusSeconds(60*60*2))
        .subject(name)
        .build();
        JwtEncoderParameters params=JwtEncoderParameters.from(claimSet);
        return encoder.encode(params).getTokenValue();
    }
}
