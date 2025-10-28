package com.example.demo.service;
import com.example.demo.dto.ClienteDto;
import com.example.demo.entity.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ClienteService {

    List<ClienteDto> findAll();

    ResponseEntity<ClienteDto> mostrarClientePorId(Integer id);

    ClienteDto createCliente(ClienteDto clienteDto);

    //void deleteCliente(Integer id);

}