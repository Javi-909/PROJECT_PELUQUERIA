package com.example.demo.controller;


import com.example.demo.dto.ServicioPeluCreacionDto;
import com.example.demo.dto.ServicioPeluDto;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.test.context.support.WithMockUser;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest

// 2. CONFIGURA EL SIMULADOR DE LLAMADAS HTTP
@AutoConfigureMockMvc
// 3. USA LA CONFIGURACIÓN DE TEST (H2) en vez de PostgreSQL
@ActiveProfiles("test")
// 4. HACE ROLLBACK AL TERMINAR CADA TEST (Base de datos siempre limpia)
@Transactional

public class ServicioPeluControllerTest {

    @Autowired
    private MockMvc mockMvc; // Para lanzar peticiones a la API

    @Autowired
    private reservaRepository reservaRepo; // Para comprobar la BD H2 directamente

    @Autowired
    private ObjectMapper objectMapper;

    // Repositorios necesarios para crear datos previos
    @Autowired
    private clienteRepository clienteRepo;

    @Autowired
    private peluqueriaRepository peluRepo;

    @Autowired
    private servicioRepository servicioRepo;

    @Autowired
    private servicioPeluRepository servicioPeluRepo;

    @Autowired
    private reservaClienteRepository reservaClienteRepo;

    @Test
    @WithMockUser
    void addServicioToPeluqueria_DeberiaCrearServicioPelu() throws Exception {
        // Crear peluqueria
        Peluqueria pelu = new Peluqueria();
        pelu.setNombre("Pelu Test");
        pelu.setEmail("pelu@test.com");
        pelu.setDireccion("Calle Test 10");
        pelu.setTelefono(600000001);
        pelu.setPassword("pwd");
        Peluqueria savedPelu = peluRepo.save(pelu);

        // Crear servicio genérico
        Servicio servicio = new Servicio();
        servicio.setNombre("Corte VIP");
        servicio.setDescripcion("Corte especial");
        Servicio savedServicio = servicioRepo.save(servicio);

        // Construir DTO de creación
        int precio = 50;
        int duracion = 45;
       ServicioPeluCreacionDto request = new ServicioPeluCreacionDto(savedServicio.getId(), savedPelu.getId(), precio, duracion);

        // WHEN: POST a /serviciopelu/add
        String resp = mockMvc.perform(post("/serviciopelu/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precio", is(precio)))
                .andExpect(jsonPath("$.duracion", is(duracion)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // THEN: comprobar persistencia
        ServicioPeluDto creado = objectMapper.readValue(resp, ServicioPeluDto.class);
        assertEquals(1, servicioPeluRepo.count());
        ServicioPelu stored = servicioPeluRepo.findAll().get(0);
        assertEquals(savedPelu.getId(), stored.getPeluqueriaId());
        assertEquals(savedServicio.getId(), stored.getServicioId());
        assertEquals(precio, stored.getPrecio());
        assertEquals(duracion, stored.getDuracion());
    }

}
