package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Peluqueria;
import com.example.demo.repository.clienteRepository;
import com.example.demo.repository.peluqueriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;



@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock //simulamos los escenarios
    private peluqueriaRepository peluqueriaRepository1;

    @Mock
    private clienteRepository clienteRepository1;

    @InjectMocks
    private AuthController authController;


    @Test //Comprueba que un Cliente se ha registrado y lo encuentra
    public void login_ClienteExisteYpassCorrecta(){

        String mail = "juan@gmail.com";
        LoginRequestDto request = new LoginRequestDto(mail, "1234");

        //Creamos un cliente ficticio
        Cliente clienteMock = new Cliente();
        clienteMock.setId(1);
        clienteMock.setNombre("Juan");
        clienteMock.setEmail(mail);
        clienteMock.setPassword("1234");

        //Cuando busques todos los clientes, devuelve el ficticio
        doReturn(clienteMock).when(clienteRepository1).findByEmail(mail);

        ResponseEntity<LoginResponseDto> respuesta = authController.login(request);

        //Verificamos
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("CLIENTE", respuesta.getBody().getRole());
        assertEquals("Juan", respuesta.getBody().getNombre());
    }


    @Test //Comprueba que un Negocio se ha registrado y lo encuentra
    public void login_NegocioYpassCorrecta(){
        LoginRequestDto request = new LoginRequestDto("negocio@gmail.com","1234");

        Peluqueria peluqueriaMock = new Peluqueria();
        peluqueriaMock.setId(2);
        peluqueriaMock.setEmail("negocio@gmail.com");
        peluqueriaMock.setPassword("1234");

        doReturn(peluqueriaMock).when(peluqueriaRepository1).findByEmail("negocio@gmail.com");

        ResponseEntity<LoginResponseDto> respuesta = authController.login(request);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("NEGOCIO", respuesta.getBody().getRole());
    }


    @Test //Comprueba que un usuario no existe
    public void login_UsuarioNoExiste(){
        String email = "nadie@gmail.com";
        LoginRequestDto request = new LoginRequestDto(email,"1234");

        //No lo encuentra nadie (pq no lo hemos setteado, deberia no existir)
        doReturn(null).when(clienteRepository1).findByEmail(email);
        doReturn(null).when(peluqueriaRepository1).findByEmail(email);

        ResponseEntity<LoginResponseDto> respuesta = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, respuesta.getStatusCode());
    }

    @Test   //Comprueba que el login tiene contraseña incorrecta
    public void login_PasswordIncorrect(){

        String email = "juan@gmail.com";
        LoginRequestDto request = new LoginRequestDto(email,"pass_falsa");

        //Creamos un nuevo cliente pero con otra contraseña
        Cliente clienteMock = new Cliente();
        clienteMock.setEmail(email);
        clienteMock.setPassword("1234");

        //Encuentra el cliente con el email indicado
        doReturn(clienteMock).when(clienteRepository1).findByEmail(email);

        //pero la contraseña no coincide
        ResponseEntity<LoginResponseDto> respuesta = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, respuesta.getStatusCode());
    }

}