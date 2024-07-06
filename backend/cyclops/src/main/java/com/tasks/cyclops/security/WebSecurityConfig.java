package com.tasks.cyclops.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.authorizeHttpRequests(
            auth->auth.requestMatchers(new AntPathRequestMatcher("/auth/*", "POST")).permitAll()
            .anyRequest().authenticated()
        );
        http.sessionManagement(
            session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.headers(
            headers->headers.frameOptions(frame->frame.sameOrigin())
        );
        http.csrf(csrf->csrf.disable());
        http.httpBasic(Customizer.withDefaults());
        http.oauth2ResourceServer(
            oauth2->oauth2.jwt(Customizer.withDefaults())
            );
        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public KeyPair keyPair(){
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator=KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.genKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public RSAKey rsaKey(KeyPair keypair){
        return new RSAKey.Builder((RSAPublicKey)keypair.getPublic())
        .privateKey(keypair.getPrivate())
        .keyID(UUID.randomUUID().toString())
        .build();
    }

    @Bean
    public JWKSource<SecurityContext> jskSource(RSAKey rsaKey){
        var jwkSet=new JWKSet(rsaKey);
        return (jwkSelector,context)->jwkSelector.select(jwkSet);
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }
    @Bean
    public JwtDecoder JwtDecoder(RSAKey rsaKey) throws JOSEException{
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }
    
}
