package com.example.demo.service.implementacion;

import com.example.demo.dto.ServicioDto;
import com.example.demo.dto.ServicioPeluDto;
import com.example.demo.entity.Servicio;

import com.example.demo.entity.ServicioPelu;
import com.example.demo.mapper.ServicioMapper;
import com.example.demo.repository.clienteRepository;
import com.example.demo.repository.servicioPeluRepository;
import com.example.demo.service.ServicioPeluService;
import com.example.demo.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.repository.servicioRepository;


import java.util.List;
import java.util.stream.Collectors;
public class ServicioPeluServiceImpl implements ServicioPeluService {

    private clienteRepository clienteRepository1;
    private servicioPeluRepository servicioPeluRepository1;



    @Override
    public ServicioDto añadirServicioApeluqueria(Integer servicioId, Integer peluqueriaId, Integer precio, Integer duracion) {
            

        return null;

    }

}



