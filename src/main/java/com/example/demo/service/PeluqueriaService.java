package com.example.demo.service;

import com.example.demo.dto.PeluqueriaDto;

import com.example.demo.dto.ServicioPeluDto;
import com.example.demo.dto.ServicioResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PeluqueriaService {

    List<PeluqueriaDto> findAll();

    ResponseEntity<PeluqueriaDto> mostrarPeluqueriaPorId(Integer id);

    PeluqueriaDto createPeluqueria(PeluqueriaDto peluqueriaDto);

    ResponseEntity<List<ServicioResponseDto>> listarServiciosPorPeluqueria(Integer peluqueriaId);

   // void deletePeluqueria(Integer id);


}