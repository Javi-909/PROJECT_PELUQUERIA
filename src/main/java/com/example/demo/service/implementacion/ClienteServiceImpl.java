package com.example.demo.service.implementacion;

import com.example.demo.dto.ClienteDto;
import com.example.demo.entity.Cliente;
import com.example.demo.mapper.ClienteMapper;
import com.example.demo.repository.clienteRepository;
import com.example.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private clienteRepository clienteRepository1;

    @Autowired
    ClienteMapper clienteMapper;

    @Override
    public List<ClienteDto> findAll() {  //mapeo natural sin MapStruct pq daba error en postman
        List<Cliente> clientes = clienteRepository1.findAll();
        return clientes.stream().map(cliente -> {
            ClienteDto dto = new ClienteDto();
            dto.setNombre(cliente.getNombre());
            dto.setEmail(cliente.getEmail());
            dto.setGenero(cliente.getGenero());
            return dto;
        }).collect(Collectors.toList());
    }


   @Override
   public ClienteDto createCliente(ClienteDto clienteDto) {
       Cliente cliente = this.toEntity(clienteDto);
       Cliente saved = clienteRepository1.save(cliente);
       return this.toDto(saved);
   }
    @Override
    public ResponseEntity<ClienteDto> mostrarClientePorId(Integer id) {
        return clienteRepository1.findById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    private ClienteDto toDto(Cliente cliente) {  //sirve para hacer el mappeo natural (sin MapStruct)
        if (cliente == null) return null;
        ClienteDto dto = new ClienteDto();
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getEmail());
        dto.setGenero(cliente.getGenero());
        return dto;
    }
    private Cliente toEntity(ClienteDto clienteDto){   //mappeo de dto a entity
        if (clienteDto == null) return null;
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDto.getNombre());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setGenero(clienteDto.getGenero());
        return cliente;
    }
}


//public void deleteCliente(Integer id) {
    //clienteRepository.deleteById(id);
//}

