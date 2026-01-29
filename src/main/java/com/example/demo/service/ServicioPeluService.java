package com.example.demo.service;

import com.example.demo.dto.ServicioPeluCreacionDto;
import com.example.demo.dto.ServicioPeluDto;
import org.springframework.stereotype.Service;

@Service
public interface ServicioPeluService {

    ServicioPeluDto añadirServicioApeluqueria(ServicioPeluCreacionDto request);

}
