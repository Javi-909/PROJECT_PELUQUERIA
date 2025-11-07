package com.example.demo.service;

import com.example.demo.dto.ReservaClienteDto;
import com.example.demo.dto.ReservaDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservaService {

    ReservaDto createReserva(Integer clienteId, ReservaDto dto);

    List<ReservaClienteDto> findByClienteId(Integer clienteId);

   // void cancelaReserva(Integer clienteId, Integer reservaId);

}
