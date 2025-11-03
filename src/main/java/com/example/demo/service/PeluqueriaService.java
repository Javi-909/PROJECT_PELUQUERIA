package com.example.demo.service;

import com.example.demo.dto.*;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PeluqueriaService {

    List<PeluqueriaDto> findAll();

    ResponseEntity<PeluqueriaDto> mostrarPeluqueriaPorId(Integer id);

    PeluqueriaDto createPeluqueria(PeluqueriaDto peluqueriaDto);

    ResponseEntity<List<ServicioResponseDto>> listarServiciosPorPeluqueria(Integer peluqueriaId);

    void deletePeluqueria(Integer id);

    ResponseEntity<HorarioDto> createHorario(Integer peluqueriaId, HorarioDto horarioDto);

    public void deleteHorario(Integer id);

    public ResponseEntity<HorarioDto> actualizarHorario(Integer id, HorarioDto horarioDto);

}