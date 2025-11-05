package com.example.securityservice.Configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final RsaKeys rsaKeys;
    public final  PasswordEncoder passwordEncoder;
    public SecurityConfiguration(RsaKeys RsaKeys, PasswordEncoder passwordEncoder) {
        this.rsaKeys = RsaKeys;
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
    public AuthenticationManager authenticationManager( UserDetailsManager userDetailsManager) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsManager);
        return new ProviderManager(daoAuthenticationProvider);

    }
    @Bean
    public UserDetailsManager userDetailsManager() {
        return new InMemoryUserDetailsManager(User.withUsername("asmae").password(passwordEncoder.encode("123")).authorities("Admin").build(),
                User.withUsername("fatima").password(passwordEncoder.encode("1234")).authorities("user").build(),
                User.withUsername("yousra").password(passwordEncoder.encode("01234")).authorities("user").build());


    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        return httpSecurity
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/login").permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/refresh").permitAll())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }
    @Bean
    JwtEncoder jwtEncoder() throws Exception {
        var pub  = PemUtils.readPublicKey(rsaKeys.publicKey());
        var priv = PemUtils.readPrivateKey(rsaKeys.privateKey());
        var jwk = new com.nimbusds.jose.jwk.RSAKey.Builder(pub).privateKey(priv).build();
        var source = new com.nimbusds.jose.jwk.source.ImmutableJWKSet<>(
                new com.nimbusds.jose.jwk.JWKSet(jwk)
        );
        return new NimbusJwtEncoder(source);
    }

    @Bean
    JwtDecoder jwtDecoder() throws Exception {
        var pub = PemUtils.readPublicKey(rsaKeys.publicKey());
        return NimbusJwtDecoder.withPublicKey(pub).build();
    }

}
