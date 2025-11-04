package com.example.demo.controller;

import com.example.demo.dto.ServicioPeluCreacionDto;
import com.example.demo.dto.ServicioPeluDto;
import com.example.demo.service.ServicioPeluService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/serviciopelu")
public class ServicioPeluController {

    @Autowired
    private ServicioPeluService servicioPeluService;

    @PostMapping("/add") //se ha tenido que crear la clase auxililar de servicioPeluCreacion para poder hacer bien el postman
    public ServicioPeluDto añadirServicioApeluqueria(@RequestBody ServicioPeluCreacionDto request) {
        return servicioPeluService.añadirServicioApeluqueria(request);
    }
}
