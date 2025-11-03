package com.example.demo.service;

import com.example.demo.dto.ClienteDto;
import com.example.demo.dto.ReservaDto;
import com.example.demo.entity.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReservaService {

    ReservaDto createReserva(Integer clienteId, CreateReservaDto dto);

    List<ReservaDto> findByClienteId(Integer clienteId);

    void cancelReserva(Integer clienteId, Integer reservaId);

}
