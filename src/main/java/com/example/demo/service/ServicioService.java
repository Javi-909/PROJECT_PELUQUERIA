package com.example.demo.service;
import com.example.demo.dto.ServicioDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicioService {

    List<ServicioDto> findAll();

    ResponseEntity<ServicioDto> mostrarServicioPorId(Integer id);

    ServicioDto createServicio(ServicioDto servicioDto);

    //ResponseEntity<List<ServicioPeluDto>> listarServiciosPorPeluqueria(Integer peluqueria_id);

    void deleteServicio(Integer id);

}