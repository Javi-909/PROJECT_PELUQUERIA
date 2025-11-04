package com.example.demo.controller;

import com.example.demo.dto.ServicioDto;
import com.example.demo.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;


@RestController
@RequestMapping("/servicio")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping("/findAll")
    public List<ServicioDto> findAll() {
        return servicioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioDto> mostrarServicioPorId(@PathVariable Integer id) {
        return servicioService.mostrarServicioPorId(id);
    }

    @PostMapping("/create")
    public ServicioDto createServicio(@RequestBody ServicioDto servicioDto) {
        return servicioService.createServicio(servicioDto);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteServicio(@PathVariable Integer id) {
        servicioService.deleteServicio(id);
    }

}