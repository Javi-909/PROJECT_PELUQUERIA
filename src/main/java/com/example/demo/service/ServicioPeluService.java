package com.example.demo.service;


import com.example.demo.dto.ServicioDto;
import org.springframework.stereotype.Service;

@Service
public interface ServicioPeluService {

    ServicioDto añadirServicioApeluqueria(Integer servicioId, Integer peluqueriaId, Integer precio, Integer duracion);

}
