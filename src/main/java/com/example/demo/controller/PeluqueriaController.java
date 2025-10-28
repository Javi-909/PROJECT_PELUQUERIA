package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.Servicio;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/peluqueria")
public class PeluqueriaController {

    @Autowired
    private PeluqueriaService peluqueriaService;
    private ServicioService servicioService;

    @GetMapping("/findAll")
    public List<PeluqueriaDto> findAll() {
        return peluqueriaService.findAll(); //esto devuelve la lista de peluqueriaDto
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeluqueriaDto> mostrarPeluqueriaPorId(@PathVariable Integer id){
        return peluqueriaService.mostrarPeluqueriaPorId(id);
    }

    @PostMapping("/create")
    public PeluqueriaDto createPeluqueria(@RequestBody PeluqueriaDto peluqueriaDto) {
        return peluqueriaService.createPeluqueria(peluqueriaDto);
    }

    @GetMapping("/{id}/servicios")
    public ResponseEntity<List<ServicioResponseDto>> listarServiciosPorPeluqueria(@PathVariable Integer id){
        return peluqueriaService.listarServiciosPorPeluqueria(id);
    }

    //@DeleteMapping("/delete")
    //public void deletePeluqueria(@PathVariable Long id) {
        //peluqueriaService.deletePeluqueria(id);
    }



    // ...otros métodos (mostrarClienteId,actualizarClienteId...)

