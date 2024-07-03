package com.ejemplo.security.config;

import com.ejemplo.security.model.OurUser;
import com.ejemplo.security.repository.OurUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Configuration
public class OurUserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private OurUserRepository ourUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<OurUser> user = ourUserRepository.findByEmail(email);
        return user.map(OurUserInfoDetails::new).orElseThrow(()-> new UsernameNotFoundException("Usuario no existe"));
    }

}
