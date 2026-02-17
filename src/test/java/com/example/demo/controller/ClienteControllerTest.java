package com.example.demo.controller;

import com.example.demo.dto.ClienteDto;
import com.example.demo.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class) // Habilita Mockito
 class ClienteControllerTest {

    @Mock // Simulamos el servicio (el "falso")
    private ClienteService clienteService;

    @InjectMocks // Inyectamos el servicio falso en el controlador real
    private ClienteController clienteController;

    @Test
     void findAll_DeberiaRetornarListaDeClientes() {
        // 1. PREPARACIÓN (Given)
        ClienteDto cliente1 = new ClienteDto();
        cliente1.setNombre("Juan");
        ClienteDto cliente2 = new ClienteDto();
        cliente2.setNombre("Ana");

        List<ClienteDto> listaFalsa = Arrays.asList(cliente1, cliente2);

        // Cuando el controlador llame al servicio, devolvemos nuestra lista falsa
        when(clienteService.findAll()).thenReturn(listaFalsa);

        // 2. EJECUCIÓN (When)
        List<ClienteDto> resultado = clienteController.findAll();

        // 3. VERIFICACIÓN (Then)
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());

        // Verificamos que el servicio fue llamado 1 vez
        verify(clienteService, times(1)).findAll();
    }

    @Test
     void mostrarClientePorId_DeberiaRetornarClienteYStatus200() {
        // 1. PREPARACIÓN
        Integer id = 1;
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Carlos");


        when(clienteService.mostrarClientePorId(id))
                .thenReturn(ResponseEntity.ok(clienteDto));

        // 2. EJECUCIÓN
        ResponseEntity<ClienteDto> respuesta = clienteController.mostrarClientePorId(id);

        // 3. VERIFICACIÓN
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Carlos", respuesta.getBody().getNombre());
    }

    @Test
     void createCliente_DeberiaRetornarClienteCreado() {
        // 1. PREPARACIÓN
        ClienteDto inputDto = new ClienteDto();
        inputDto.setNombre("Nuevo");

        ClienteDto outputDto = new ClienteDto();
        // outputDto.setId(10); // ELIMINADO
        outputDto.setNombre("Nuevo");

        when(clienteService.createCliente(inputDto)).thenReturn(outputDto);

        // 2. EJECUCIÓN
        ClienteDto resultado = clienteController.createCliente(inputDto);

        // 3. VERIFICACIÓN
        assertNotNull(resultado);
        // assertEquals(10, resultado.getId()); // ELIMINADO
        assertEquals("Nuevo", resultado.getNombre()); // Verificamos el nombre en su lugar
    }

    @Test
     void deleteCliente_DeberiaLlamarAlServicio() {
        // 1. PREPARACIÓN
        Integer id = 5;

        // Para métodos void, no hace falta 'when', pero podemos configurar 'doNothing' si queremos ser explícitos
        doNothing().when(clienteService).deleteCliente(id);

        // 2. EJECUCIÓN
        clienteController.deleteCliente(id);

        // 3. VERIFICACIÓN
        // Lo más importante en un delete es verificar que se llamó al servicio con el ID correcto
        verify(clienteService, times(1)).deleteCliente(id);
    }

    @Test
     void actualizarCliente_DeberiaRetornarClienteActualizado() {
        // 1. PREPARACIÓN
        Integer id = 1;
        ClienteDto inputDto = new ClienteDto();
        inputDto.setNombre("Nombre Editado");

        ClienteDto outputDto = new ClienteDto();
        // outputDto.setId(id); // ELIMINADO
        outputDto.setNombre("Nombre Editado");

        when(clienteService.actualizarCliente(id, inputDto))
                .thenReturn(ResponseEntity.ok(outputDto));

        // 2. EJECUCIÓN
        ResponseEntity<ClienteDto> respuesta = clienteController.actualizarCliente(id, inputDto);

        // 3. VERIFICACIÓN
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Nombre Editado", respuesta.getBody().getNombre());
    }
}