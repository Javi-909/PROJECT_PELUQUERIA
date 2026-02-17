package com.example.demo.controller;

import com.example.demo.dto.ReservaDto;
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

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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

 class ReservaControllerTest {

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
    void createReserva() throws Exception {
        // Creamos un cliente en H2
        Cliente cliente = new Cliente();
        cliente.setNombre("Cliente Test");
        cliente.setEmail("cliente@test.com");
        cliente.setGenero("M");
        cliente.setPassword("pwd");
        Cliente savedCliente = clienteRepo.save(cliente);

        // Creamos una peluquería en H2
        Peluqueria pelu = new Peluqueria();
        pelu.setNombre("Pelu Test");
        pelu.setEmail("pelu@test.com");
        pelu.setDireccion("Calle Demo 1");
        pelu.setTelefono(600111222);
        pelu.setPassword("pwd");
        Peluqueria savedPelu = peluRepo.save(pelu);

        // Creamos un servicio genérico
        Servicio servicio = new Servicio();
        servicio.setNombre("Corte");
        servicio.setDescripcion("Corte masculino");
        Servicio savedServicio = servicioRepo.save(servicio);

        // Asociamos servicio a peluquería mediante ServicioPelu
        ServicioPelu sp = new ServicioPelu();
        sp.setPeluqueria_id(savedPelu.getId());
        sp.setServicioId(savedServicio.getId());
        sp.setPrecio(20);
        sp.setDuracion(30);
        ServicioPelu savedSp = servicioPeluRepo.save(sp);

        // Construimos DTO de reserva con idServicioPelu, fecha y hora
        ReservaDto nuevaReserva = new ReservaDto();
        nuevaReserva.setIdServicioPelu(savedSp.getId());
        nuevaReserva.setFecha(LocalDate.parse("2026-07-07"));
        nuevaReserva.setHora(LocalTime.parse("10:00:00"));

        // WHEN se llama al endpoint de creación de reserva con el clienteId y el DTO
        String response = mockMvc.perform(post("/reserva/create/" + savedCliente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevaReserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.idServicioPelu", is(savedSp.getId())))
                .andExpect(jsonPath("$.fecha", is("2026-07-07")))
                .andExpect(jsonPath("$.hora", is("10:00:00")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Entonces la reserva se guarda en la BD H2
        ReservaDto reservaCreada = objectMapper.readValue(response, ReservaDto.class);
        com.example.demo.entity.Reserva reservaEnBD = reservaRepo.findById(reservaCreada.getId()).orElseThrow();

        // Comprobaciones: cliente asociado, servicio asociado y fecha/hora
        // La relación cliente-reserva se guarda en la tabla reservacliente
        java.util.List<com.example.demo.entity.ReservaCliente> mappings = reservaClienteRepo.findByReserva_id(reservaEnBD.getId());
        assertEquals(1, mappings.size(), "Debe existir un mapping reservacliente para la reserva");
        assertEquals(savedCliente.getId(), mappings.get(0).getCliente_id());
        // El servicio asociado se guarda como idServicioPelu en la entidad reserva
        assertEquals(savedSp.getId(), reservaEnBD.getIdServicioPelu());
        assertEquals(reservaCreada.getFecha(), reservaEnBD.getFecha());
        assertEquals(reservaCreada.getHora(), reservaEnBD.getHora());
    }

    @Test
    @WithMockUser
    void cancelaReserva_DeberiaMarcarReservaComoCancelada() throws Exception {
        // Crear cliente
        Cliente cliente = new Cliente();
        cliente.setNombre("Cliente Cancelar");
        cliente.setEmail("cancel@test.com");
        cliente.setGenero("F");
        cliente.setPassword("pwd");
        Cliente savedCliente = clienteRepo.save(cliente);

        // Crear peluqueria
        Peluqueria pelu = new Peluqueria();
        pelu.setNombre("Pelu Cancel");
        pelu.setEmail("pelucancel@test.com");
        pelu.setDireccion("Calle Cancel 1");
        pelu.setTelefono(600222333);
        pelu.setPassword("pwd");
        Peluqueria savedPelu = peluRepo.save(pelu);

        // Crear servicio y asociar a pelu
        Servicio servicio = new Servicio();
        servicio.setNombre("Tinte");
        servicio.setDescripcion("Tinte completo");
        Servicio savedServicio = servicioRepo.save(servicio);

        ServicioPelu sp = new ServicioPelu();
        sp.setPeluqueria_id(savedPelu.getId());
        sp.setServicioId(savedServicio.getId());
        sp.setPrecio(30);
        sp.setDuracion(45);
        ServicioPelu savedSp = servicioPeluRepo.save(sp);

        // Crear reserva DTO
        ReservaDto nuevaReserva = new ReservaDto();
        nuevaReserva.setIdServicioPelu(savedSp.getId());
        nuevaReserva.setFecha(LocalDate.parse("2026-08-08"));
        nuevaReserva.setHora(LocalTime.parse("11:30:00"));

        // Crear reserva mediante el endpoint
        String createResp = mockMvc.perform(post("/reserva/create/" + savedCliente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevaReserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ReservaDto creada = objectMapper.readValue(createResp, ReservaDto.class);

        // Comprobamos que mapping cliente-reserva existe
        java.util.List<com.example.demo.entity.ReservaCliente> mappings = reservaClienteRepo.findByReserva_id(creada.getId());
        assertEquals(1, mappings.size());
        assertEquals(savedCliente.getId(), mappings.get(0).getCliente_id());

        // WHEN: cancelamos la reserva
        String cancelResp = mockMvc.perform(delete("/reserva/cancelareserva/" + creada.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(creada.getId())))
                .andExpect(jsonPath("$.estado", is("CANCELADA")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ReservaDto canceladaDto = objectMapper.readValue(cancelResp, ReservaDto.class);

        // THEN: la entidad en BD debe estar marcada como CANCELADA
       Reserva reservaEnBD = reservaRepo.findById(creada.getId()).orElseThrow();
        assertEquals(canceladaDto.getEstado(), reservaEnBD.getEstado());
        assertEquals(com.example.demo.entity.EstadoReserva.CANCELADA, reservaEnBD.getEstado());
    }

    @Test
    @WithMockUser
    void findByClienteId_DeberiaDevolverReservasDelCliente() throws Exception {
        // Crear cliente
        Cliente cliente = new Cliente();
        cliente.setNombre("Cliente Lista");
        cliente.setEmail("list@test.com");
        cliente.setGenero("M");
        cliente.setPassword("pwd");
        Cliente savedCliente = clienteRepo.save(cliente);

        // Crear peluqueria
        Peluqueria pelu = new Peluqueria();
        pelu.setNombre("Pelu Lista");
        pelu.setEmail("pelulist@test.com");
        pelu.setDireccion("Calle Lista 1");
        pelu.setTelefono(600333444);
        pelu.setPassword("pwd");
        Peluqueria savedPelu = peluRepo.save(pelu);

        // Crear servicio y asociar a pelu
        Servicio servicio = new Servicio();
        servicio.setNombre("Peinar");
        servicio.setDescripcion("Peinado");
        Servicio savedServicio = servicioRepo.save(servicio);

        ServicioPelu sp = new ServicioPelu();
        sp.setPeluqueria_id(savedPelu.getId());
        sp.setServicioId(savedServicio.getId());
        sp.setPrecio(15);
        sp.setDuracion(20);
        ServicioPelu savedSp = servicioPeluRepo.save(sp);

        // Crear reserva DTO y crear reserva por POST
        ReservaDto nuevaReserva = new ReservaDto();
        nuevaReserva.setIdServicioPelu(savedSp.getId());
        nuevaReserva.setFecha(LocalDate.parse("2026-09-01"));
        nuevaReserva.setHora(LocalTime.parse("09:30:00"));

        String createResp = mockMvc.perform(post("/reserva/create/" + savedCliente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevaReserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ReservaDto creada = objectMapper.readValue(createResp, ReservaDto.class);

        // WHEN: llamamos al endpoint para obteher reservas del cliente
        mockMvc.perform(get("/reserva/" + savedCliente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].cliente_id", is(savedCliente.getId())))
                .andExpect(jsonPath("$[0].reserva_id", is(creada.getId())));
    }

    @Test
    @WithMockUser
    void getReservasDePeluqueria_DeberiaDevolverReservasNegocio() throws Exception {
        // Crear peluqueria
        Peluqueria pelu = new Peluqueria();
        pelu.setNombre("Pelu Negocio");
        pelu.setEmail("negocio@test.com");
        pelu.setDireccion("Calle Negocio 1");
        pelu.setTelefono(601234567);
        pelu.setPassword("pwd");
        Peluqueria savedPelu = peluRepo.save(pelu);

        // Crear servicio genérico
        Servicio servicio = new Servicio();
        servicio.setNombre("Corte Premium");
        servicio.setDescripcion("Corte con estilo");
        Servicio savedServicio = servicioRepo.save(servicio);

        // Asociar servicio a peluqueria
        ServicioPelu sp = new ServicioPelu();
        sp.setPeluqueria_id(savedPelu.getId());
        sp.setServicioId(savedServicio.getId());
        sp.setPrecio(40);
        sp.setDuracion(45);
        ServicioPelu savedSp = servicioPeluRepo.save(sp);

        // Crear cliente y reservar
        Cliente cliente = new Cliente();
        cliente.setNombre("Cliente Negocio");
        cliente.setEmail("cliente.negocio@test.com");
        cliente.setGenero("M");
        cliente.setPassword("pwd");
        Cliente savedCliente = clienteRepo.save(cliente);

        // Crear reserva DTO
        ReservaDto nuevaReserva = new ReservaDto();
        nuevaReserva.setIdServicioPelu(savedSp.getId());
        nuevaReserva.setFecha(LocalDate.parse("2026-10-10"));
        nuevaReserva.setHora(LocalTime.parse("14:00:00"));

        // Crear reserva mediante POST
        String createResp = mockMvc.perform(post("/reserva/create/" + savedCliente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevaReserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ReservaDto creada = objectMapper.readValue(createResp, ReservaDto.class);

        // WHEN: solicitamos reservas del negocio
        mockMvc.perform(get("/reserva/peluqueria/" + savedPelu.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].id", is(creada.getId())))
                .andExpect(jsonPath("$[0].nombreCliente", is(savedCliente.getNombre())))
                .andExpect(jsonPath("$[0].emailCliente", is(savedCliente.getEmail())))
                .andExpect(jsonPath("$[0].nombreServicio", is(savedServicio.getNombre())))
                .andExpect(jsonPath("$[0].precioServicio", is(40)))
                .andExpect(jsonPath("$[0].estado", is("PENDIENTE")));
    }




}
