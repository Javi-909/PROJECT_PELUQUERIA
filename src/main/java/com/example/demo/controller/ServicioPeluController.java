package com.example.demo.controller;


import com.example.demo.dto.ClienteDto;
import com.example.demo.dto.ServicioDto;
import com.example.demo.service.ServicioPeluService;
import com.example.demo.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/serviciopelu")
public class ServicioPeluController {

    @Autowired
    private ServicioPeluService servicioPeluService;

    @PostMapping("/create")
    public ServicioDto añadirServicioApeluqueria( Integer ServicioId, Integer PeluqueriaId, Integer precio, Integer duracion) {
        return servicioPeluService.createCliente(servicioDto);
    }
}
