package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ServicioDto> mostrarServicioPorId(@PathVariable Integer id){
        return servicioService.mostrarServicioPorId(id);
    }

    @PostMapping("/create")
    public ServicioDto createServicio(@RequestBody ServicioDto servicioDto) {
        return servicioService.createServicio(servicioDto);
    }




    //@DeleteMapping("/delete")
    //public void deleteServicio(@PathVariable Long id){
       // servicioService.deleteServicio(id);
    }

