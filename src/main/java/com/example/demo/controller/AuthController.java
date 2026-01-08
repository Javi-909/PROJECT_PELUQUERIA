package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Peluqueria;
import com.example.demo.repository.clienteRepository;
import com.example.demo.repository.peluqueriaRepository;
import com.example.demo.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/auth")
public class AuthController {  //GESTIONA EL TEMA DE INICIO DE SESIÓN

    @Autowired
    private clienteRepository clienteRepository1;

    @Autowired
    private peluqueriaRepository peluqueriaRepository1;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {

        //  BUSCAR EN CLIENTES
        Cliente cliente = clienteRepository1.findByEmail(loginRequest.getEmail());

            if ( cliente != null && cliente.getPassword().equals(loginRequest.getPassword())) {
                // ¡Es un CLIENTE!

                String token = jwtService.generateToken(cliente.getEmail(), "CLIENTE", cliente.getId());

                return ResponseEntity.ok(new LoginResponseDto(
                        cliente.getId(),
                        cliente.getNombre(),
                        cliente.getEmail(),
                        "CLIENTE", // Rol importante
                        token
                ));
        }

        //  SI NO ES CLIENTE, BUSCAR EN PELUQUERÍAS
        Peluqueria peluqueria = peluqueriaRepository1.findByEmail(loginRequest.getEmail());

            if ( peluqueria != null && peluqueria.getPassword().equals(loginRequest.getPassword())) {
                // ¡Es una PELUQUERÍA (NEGOCIO)!  Generamos el token
                String token = jwtService.generateToken(peluqueria.getEmail(), "NEGOCIO", peluqueria.getId());

                return ResponseEntity.ok(new LoginResponseDto(
                        peluqueria.getId(),
                        peluqueria.getNombre(),
                        peluqueria.getEmail(),
                        "NEGOCIO", // Rol importante
                        token
                ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //NO EXISTE
    }
}
