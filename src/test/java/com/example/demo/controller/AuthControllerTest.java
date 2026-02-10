package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Peluqueria;
import com.example.demo.repository.clienteRepository;
import com.example.demo.repository.peluqueriaRepository;
import com.example.demo.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    // 1. Mockeamos TODAS las dependencias que usa tu AuthController
    @Mock
    private clienteRepository clienteRepository1;

    @Mock
    private peluqueriaRepository peluqueriaRepository1;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    // 2. Inyectamos los mocks en el controlador real
    @InjectMocks
    private AuthController authController;

    @Test
    public void login_ClienteExisteYPassCorrecta_DevuelveToken() {
        // DATOS
        String email = "cliente@test.com";
        String password = "123"; // La que envía el usuario
        String encodedPassword = "$2a$10$hash..."; // La que está en BD

        LoginRequestDto request = new LoginRequestDto(email, password);

        Cliente clienteMock = new Cliente();
        clienteMock.setId(1);
        clienteMock.setEmail(email);
        clienteMock.setNombre("Pepe");
        clienteMock.setPassword(encodedPassword);

        // SIMULACIONES (MOCKS)
        // 1. Que el repositorio encuentre al cliente
        when(clienteRepository1.findByEmail(email)).thenReturn(clienteMock);

        // 2. Que el PasswordEncoder diga que las contraseñas coinciden
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        // 3. Que el JwtService genere un token falso
        when(jwtService.generateToken(anyString(), anyString(), anyInt())).thenReturn("TOKEN_FALSO_123");

        // EJECUCIÓN
        ResponseEntity<LoginResponseDto> response = authController.login(request);

        // VERIFICACIÓN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TOKEN_FALSO_123", response.getBody().getToken());
        assertEquals("CLIENTE", response.getBody().getRole());
    }

    @Test
    public void login_NegocioExisteYPassCorrecta_DevuelveToken() {
        // DATOS
        String email = "negocio@test.com";
        String password = "123";
        LoginRequestDto request = new LoginRequestDto(email, password);

        Peluqueria peluMock = new Peluqueria();
        peluMock.setId(5);
        peluMock.setEmail(email);
        peluMock.setNombre("Barber Shop");
        peluMock.setPassword("hash_negocio");

        // SIMULACIONES
        // 1. Cliente NO existe
        when(clienteRepository1.findByEmail(email)).thenReturn(null);

        // 2. Peluquería SÍ existe
        when(peluqueriaRepository1.findByEmail(email)).thenReturn(peluMock);

        // 3. Contraseña coincide
        when(passwordEncoder.matches(password, "hash_negocio")).thenReturn(true);

        // 4. Token generado
        when(jwtService.generateToken(anyString(), anyString(), anyInt())).thenReturn("TOKEN_NEGOCIO");

        // EJECUCIÓN
        ResponseEntity<LoginResponseDto> response = authController.login(request);

        // VERIFICACIÓN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("NEGOCIO", response.getBody().getRole());
    }

    @Test
    public void login_PasswordIncorrecta_Devuelve401() {
        String email = "cliente@test.com";
        String password = "mal";
        LoginRequestDto request = new LoginRequestDto(email, password);

        Cliente clienteMock = new Cliente();
        clienteMock.setEmail(email);
        clienteMock.setPassword("hash_real");

        // Encuentra usuario...
        when(clienteRepository1.findByEmail(email)).thenReturn(clienteMock);

        // ...pero la contraseña NO coincide
        when(passwordEncoder.matches(password, "hash_real")).thenReturn(false);

        ResponseEntity<LoginResponseDto> response = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void login_UsuarioNoExiste_Devuelve401() {
        LoginRequestDto request = new LoginRequestDto("nadie@test.com", "123");

        // No encuentra nada en ningún lado
        when(clienteRepository1.findByEmail("nadie@test.com")).thenReturn(null);
        when(peluqueriaRepository1.findByEmail("nadie@test.com")).thenReturn(null);

        ResponseEntity<LoginResponseDto> response = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}