package com.example.securityservice.Controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController

public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtEncoder  jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtEncoder jwtEncoder
    , JwtDecoder jwtDecoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }
    @PostMapping("/login")
    public Map<String,String> login(String username, String password) {
        Map<String,String> MapToken = new HashMap<>();
        Instant instant = Instant.now();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        String scope = authentication.getAuthorities().stream().map(auth-> auth.getAuthority()).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet_AccesToken = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuer("SecurityConfiguration")
                .issuedAt(instant)
                .expiresAt(instant.plus(2, ChronoUnit.MINUTES))
                .claim("scope",scope)
                .build();
        String Access_token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet_AccesToken)).getTokenValue();
        JwtClaimsSet jwtClaimsSet_RefreshToken = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuer("SecurityConfiguration")
                .issuedAt(instant)
                .expiresAt(instant.plus(5,ChronoUnit.MINUTES))
                .build();
        String Refresh_token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet_RefreshToken)).getTokenValue();
        MapToken.put("access_token",Access_token);
        MapToken.put("refresh_token",Refresh_token);
        return MapToken ;
    };
    @PostMapping("/refresh")
    public Map<String,String> refresh(String refreshToken) {
        Map<String,String> MapToken = new HashMap<>();
        if (refreshToken==null || refreshToken.isEmpty()) {
             MapToken.put("error","refresh token is empty");
             return MapToken;
        }
        Jwt jwt = jwtDecoder.decode(refreshToken);
        String username=jwt.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);



        Instant instant = Instant.now();

        String scope = userDetails.getAuthorities().stream().map(auth-> auth.getAuthority()).collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet_acessToken = JwtClaimsSet.builder()
                .subject(userDetails.getUsername())
                .issuer("Security_Service")
                .issuedAt(instant)
                .expiresAt(instant.plus(2, ChronoUnit.MINUTES))
                .claim("scope",scope)
                .build();

        String Access_token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet_acessToken)).getTokenValue();

        MapToken.put("Access_Token",Access_token);
        MapToken.put("Refresh_Token",refreshToken);
        return MapToken;



    }



}
