package com.example.demo.controller;

import com.example.demo.dto.PeluqueriaDto;
import com.example.demo.dto.HorarioDto;
import com.example.demo.entity.Peluqueria;
import com.example.demo.repository.horarioRepository;
import com.example.demo.repository.peluqueriaRepository;
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


import java.sql.Time;

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

 class PeluqueriaControllerTest {

    @Autowired
    private MockMvc mockMvc; // Para lanzar peticiones a la API

    @Autowired
    private peluqueriaRepository peluqueriaRepo; // Para comprobar la BD H2 directamente

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private horarioRepository horarioRepo;

    @Test
    void crearPeluqueria_DeberiaGuardarEnBaseDeDatos() throws Exception{

        PeluqueriaDto nuevaPelu = new PeluqueriaDto();
        nuevaPelu.setNombre("Peluqueria Test");
        nuevaPelu.setEmail("test@gmail.com");
        nuevaPelu.setDireccion("Calle Norte 3");
        nuevaPelu.setTelefono(123456789);
        nuevaPelu.setPassword("1234");


        // WHEN (Cuando): Hacemos la petición POST real
        mockMvc.perform(post("/peluqueria/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevaPelu)))

        //THEN: Verificamos respuesta y BBDD
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Peluqueria Test")));

        assertEquals(1, peluqueriaRepo.count());   //Verificacion extra, miramos directamente a H2 para ver si se ha guardado
        assertEquals("Peluqueria Test", peluqueriaRepo.findAll().get(0).getNombre());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"}) //sirve para simular el rol de usuario (para poder comprbar el metodo delete, ya que se necesita estar logueado
    void eliminarPeluqueria_DeberiaEliminarDeBaseDeDatos() throws Exception {

        //Primero creamos una peluqueria nueva para luego eliminarla
        Peluqueria nuevaPelu = new Peluqueria();
        nuevaPelu.setNombre("Peluqueria Test");
        nuevaPelu.setEmail("test2@gmail.com");
        nuevaPelu.setDireccion("Calle Norte 3");
        nuevaPelu.setTelefono(123456789);
        nuevaPelu.setPassword("1234");

        Peluqueria guardada = peluqueriaRepo.save(nuevaPelu); //Guardamos la peluqueria en H2 y obtenemos su ID

        assertEquals(1, peluqueriaRepo.count()); //Verificamos que se ha guardado correctamente

        //WHEN: llamamos al endpoint DELETE para eliminar la peluqueria que acabamos de crear
        mockMvc.perform(delete("/peluqueria/delete/" + guardada.getId())) //Hacemos la petición DELETE a la API
                .andExpect(status().isOk());

        //THEN: Verificamos que la peluqueria se ha eliminado de la base de datos
        assertEquals(0, peluqueriaRepo.count(), "La tabla debería estar vacía"); //La base de datos debería estar
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void findAll_DeberiaDevolverListaDePeluquerias() throws Exception {
        //Creamos varias peluquerias en la base de datos
        Peluqueria pelu1 = new Peluqueria();
        pelu1.setNombre("Peluqueria Uno");
        pelu1.setEmail("test1@gmail.com");
        pelu1.setTelefono(12346789);
        pelu1.setDireccion("Calle Norte 1");
        pelu1.setPassword("1234");
        peluqueriaRepo.save(pelu1);

        Peluqueria pelu2 = new Peluqueria();
        pelu2.setNombre("Peluqueria dos");
        pelu2.setEmail("test2@gmail.com");
        pelu2.setTelefono(12246789);
        pelu2.setDireccion("Calle Norte 2");
        pelu2.setPassword("1234");
        peluqueriaRepo.save(pelu2);

        //WHEN: Llamamos al endpoint GET para obtener la lista de peluquerias
        mockMvc.perform(get("/peluqueria/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2))) //Verificamos que devuelve 2 peluquerias
                .andExpect(jsonPath("$[0].nombre", is("Peluqueria Uno"))) //Verificamos el nombre de la primera peluqueria
                .andExpect(jsonPath("$[1].nombre", is("Peluqueria dos"))); //Verificamos el nombre de la segunda peluqueria

        //THEN Verificamos que la base de datos sigue teniendo las 2 peluquerias (no se han eliminado ni modificado)
        assertEquals(2, peluqueriaRepo.count());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void crearHorario_DeberiaCrearHorarioParaPeluqueria() throws Exception {
        // Creamos una peluqueria para asociarle un horario
        Peluqueria pelu = new Peluqueria();
        pelu.setNombre("Peluqueria Horario");
        pelu.setEmail("horario@test.com");
        pelu.setDireccion("Calle Test 5");
        pelu.setTelefono(987654321);
        pelu.setPassword("pwd");
        Peluqueria saved = peluqueriaRepo.save(pelu);

        // Construimos el DTO de horario
       HorarioDto horarioDto = new HorarioDto();
        horarioDto.setDia_semana("Lunes");
        horarioDto.setHora_apertura(Time.valueOf("09:00:00"));
        horarioDto.setHora_cierre(Time.valueOf("17:00:00"));

        // WHEN: Llamamos al endpoint para crear el horario
        mockMvc.perform(post("/peluqueria/createhorario/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(horarioDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dia_semana", is("Lunes")))
                .andExpect(jsonPath("$.hora_apertura", notNullValue()))
                .andExpect(jsonPath("$.hora_cierre", notNullValue()));

        // THEN: Verificamos que se ha creado un horario en la base de datos
        assertEquals(1, horarioRepo.count());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void eliminarHorario_DeberiaEliminarHorario() throws Exception {
        // Creamos peluqueria
        Peluqueria pelu = new Peluqueria();
        pelu.setNombre("Peluqueria Horario Borrar");
        pelu.setEmail("horarioborrar@test.com");
        pelu.setDireccion("Calle Borrar 1");
        pelu.setTelefono(555111222);
        pelu.setPassword("pwd");
        Peluqueria saved = peluqueriaRepo.save(pelu);

        // Creamos el horario mediante el endpoint
        HorarioDto horarioDto = new HorarioDto();
        horarioDto.setDia_semana("Martes");
        horarioDto.setHora_apertura(Time.valueOf("08:00:00"));
        horarioDto.setHora_cierre(Time.valueOf("12:00:00"));

        mockMvc.perform(post("/peluqueria/createhorario/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(horarioDto)))
                .andExpect(status().isOk());

        // Obtenemos el id del horario creado
        Integer horarioId = horarioRepo.findAll().get(0).getId();

        // WHEN: eliminamos el horario con DELETE
        mockMvc.perform(delete("/peluqueria/deletehorario/" + horarioId))
                .andExpect(status().isOk());

        // THEN: el repositorio de horarios debe quedar vacío
        assertEquals(0, horarioRepo.count());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void actualizarHorario_DeberiaActualizarHorarioExistente() throws Exception {
        // Creamos peluqueria
        Peluqueria pelu = new Peluqueria();
        pelu.setNombre("Peluqueria Horario Actualizar");
        pelu.setEmail("pelu1@gmail.com");
        pelu.setDireccion("Calle Actualizar 1");
        pelu.setTelefono(555111222);
        pelu.setPassword("pwd");
        Peluqueria saved = peluqueriaRepo.save(pelu);

        HorarioDto horarioDto = new HorarioDto();
        horarioDto.setDia_semana("Martes");
        horarioDto.setHora_apertura(Time.valueOf("08:00:00"));
        horarioDto.setHora_cierre(Time.valueOf("12:00:00"));

        // WHEN Creamos el horario mediante el endpoint
        mockMvc.perform(post("/peluqueria/createhorario/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(horarioDto)))
                .andExpect(status().isOk());

        Integer horarioId = horarioRepo.findAll().get(0).getId();

        //THEN: Actualizamos el horario con PUT
        HorarioDto horarioActualizado = new HorarioDto();
        horarioActualizado.setDia_semana("Miércoles");
        horarioActualizado.setHora_apertura(Time.valueOf("10:00:00"));
        horarioActualizado.setHora_cierre(Time.valueOf("18:00:00"));

        // WHEN: Llamamos al endpoint PUT para actualizar el horario
        mockMvc.perform(put("/peluqueria/actualizarhorario/" + horarioId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(horarioActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dia_semana", is("Miércoles")))
                .andExpect(jsonPath("$.hora_apertura", notNullValue()))
                .andExpect(jsonPath("$.hora_cierre", notNullValue()));

        // THEN: Verificamos en la base de datos que los cambios se han aplicado
        com.example.demo.entity.Horario updated = horarioRepo.findById(horarioId).orElseThrow();
        assertEquals("Miércoles", updated.getDiaSemana());
        assertEquals(Time.valueOf("10:00:00"), updated.getHoraApertura());
        assertEquals(Time.valueOf("18:00:00"), updated.getHoraCierre());

    }
}
