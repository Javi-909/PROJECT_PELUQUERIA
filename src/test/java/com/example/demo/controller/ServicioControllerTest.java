package com.example.demo.controller;


import com.example.demo.dto.ServicioDto;
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

 class ServicioControllerTest {

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
    void findAllServicios_DeberiaRetornarLista() throws Exception {
        // Creamos dos servicios directamente en la BD H2
        Servicio s1 = new Servicio();
        s1.setNombre("Corte Básico");
        s1.setDescripcion("Corte simple");
        servicioRepo.save(s1);

        Servicio s2 = new Servicio();
        s2.setNombre("Tinte Completo");
        s2.setDescripcion("Tinte con tratamiento");
        servicioRepo.save(s2);

        // WHEN: llamamos al endpoint GET /servicio/findAll
        mockMvc.perform(get("/servicio/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.[*].nombre", containsInAnyOrder("Corte Básico", "Tinte Completo")));
    }

    @Test
    @WithMockUser
    void mostrarServicioPorId_DeberiaRetornarServicio() throws Exception {
        // Creamos un servicio en H2
        Servicio s = new Servicio();
        s.setNombre("Servicio Único");
        s.setDescripcion("Descripción única");
        Servicio saved = servicioRepo.save(s);

        // WHEN: llamamos al endpoint de obtener servicio por ID
        mockMvc.perform(get("/servicio/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(saved.getId())))
                .andExpect(jsonPath("$.nombre", is("Servicio Único")))
                .andExpect(jsonPath("$.descripcion", is("Descripción única")));
    }

    @Test
    @WithMockUser
    void createServicio_DeberiaCrearServicio() throws Exception {
        // Preparar DTO de servicio
        ServicioDto dto = new ServicioDto();
        dto.setNombre("Servicio Nuevo");
        dto.setDescripcion("Descripción nueva");

        // WHEN: POST a /servicio/create
        String resp = mockMvc.perform(post("/servicio/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Servicio Nuevo")))
                .andExpect(jsonPath("$.descripcion", is("Descripción nueva")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // THEN: verificar en BD
        ServicioDto creado = objectMapper.readValue(resp, ServicioDto.class);
        assertEquals(1, servicioRepo.count());
        Servicio encontrado = servicioRepo.findById(creado.getId()).orElseThrow();
        assertEquals("Servicio Nuevo", encontrado.getNombre());
        assertEquals("Descripción nueva", encontrado.getDescripcion());
    }

    @Test
    @WithMockUser
    void deleteServicio_DeberiaEliminarServicio() throws Exception {
        // Crear servicio en H2
        Servicio s = new Servicio();
        s.setNombre("Servicio A Eliminar");
        s.setDescripcion("Descripción a eliminar");
        Servicio saved = servicioRepo.save(s);

        // Verificamos que se ha guardado
        assertEquals(1, servicioRepo.count());

        // WHEN: llamamos al endpoint DELETE
        mockMvc.perform(delete("/servicio/delete/" + saved.getId()))
                .andExpect(status().isOk());

        // THEN: comprobamos que se ha eliminado
        assertEquals(0, servicioRepo.count());
    }

}
