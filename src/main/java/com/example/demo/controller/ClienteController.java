package com.example.demo.controller;

import com.example.demo.dto.ClienteDto;
import com.example.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/findAll")
    public List<ClienteDto> findAll() {
        return clienteService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> mostrarClientePorId(@PathVariable Integer id){
        return clienteService.mostrarClientePorId(id);
    }

    @PostMapping("/create")
    public ClienteDto createCliente(@RequestBody ClienteDto clienteDto) {
        return clienteService.createCliente(clienteDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCliente(@PathVariable Integer id){
       clienteService.deleteCliente(id);
    }


    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ClienteDto> actualizarCliente(@PathVariable Integer id, @RequestBody ClienteDto clienteDto){
        return clienteService.actualizarCliente(id,clienteDto);
    }


}
