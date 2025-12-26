package com.example.demo.config;

import com.example.demo.repository.clienteRepository;
import com.example.demo.repository.peluqueriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

private final clienteRepository clienteRepo;
private final peluqueriaRepository peluqueriaRepo;

// ESTE ES EL MÉTODO QUE SPRING SECURITY USA PARA ENCONTRAR USUARIOS
// El filtro JWT llama a esto.
@Bean
public UserDetailsService userDetailsService() {
    return username -> {
        // 1. Buscamos en la tabla CLIENTES
        var cliente = clienteRepo.findByEmail(username);
        if (cliente != null) {
            // Convertimos tu entidad Cliente a un objeto User de Spring Security
            return new User(cliente.getEmail(), cliente.getPassword(), new ArrayList<>());
        }

        // 2. Si no, buscamos en la tabla PELUQUERÍAS
        var peluqueria = peluqueriaRepo.findByEmail(username);
        if (peluqueria != null) {
            return new User(peluqueria.getEmail(), peluqueria.getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("Usuario no encontrado");
    };
}

// Configuración estándar de Spring Security
@Bean
public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
}

@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
}

@Bean
public PasswordEncoder passwordEncoder() {  //encriptacion de contraseñas
    return new BCryptPasswordEncoder();
}
}