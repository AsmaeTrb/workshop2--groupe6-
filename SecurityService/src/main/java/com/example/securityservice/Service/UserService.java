package com.example.securityservice.Service;

import com.example.securityservice.Entity.Utilisateur;
import com.example.securityservice.Repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    public final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Utilisateur utilisateur  = userRepository.findByUsername(username);
        SimpleGrantedAuthority authority =new SimpleGrantedAuthority("ROLE_"+utilisateur.getRole().name());
        return new User(utilisateur.getUsername(),
                utilisateur.getPassword(),
                List.of(authority)
        );
    }
}
