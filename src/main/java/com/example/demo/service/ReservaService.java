package com.example.demo.service;

import com.example.demo.dto.ReservaClienteDto;
import com.example.demo.dto.ReservaDto;
import com.example.demo.dto.ReservaNegocioDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservaService {

    ReservaDto createReserva(Integer clienteId, ReservaDto dto);

    List<ReservaClienteDto> findByClienteId(Integer clienteId);

    ResponseEntity<ReservaDto> cancelaReserva(Integer reservaId);

    List<ReservaNegocioDto> getReservasDePeluqueria(Integer peluqueriaId);

    ResponseEntity<ReservaDto> confirmaReserva(Integer id);

}
