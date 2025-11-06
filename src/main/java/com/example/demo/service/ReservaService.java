package com.example.demo.service;

import com.example.demo.dto.ClienteDto;
import com.example.demo.dto.ReservaClienteDto;
import com.example.demo.dto.ReservaDto;
import com.example.demo.entity.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservaService {

    ReservaDto createReserva(Integer clienteId, ReservaDto dto);

    List<ReservaClienteDto> findByClienteId(Integer clienteId);

   // void cancelaReserva(Integer clienteId, Integer reservaId);

}
