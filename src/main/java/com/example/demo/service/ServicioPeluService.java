package com.example.demo.service;


import org.springframework.stereotype.Service;

@Service
public interface ServicioPeluService {

    ServicioDto añadirServicioApeluqueria(Integer ServicioId, Integer PeluqueriaId, Integer precio, Integer duracion);

}
