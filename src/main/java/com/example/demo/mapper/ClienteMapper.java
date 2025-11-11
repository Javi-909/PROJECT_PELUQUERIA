package com.example.demo.mapper;

import com.example.demo.dto.ClienteDto;
import com.example.demo.entity.Cliente;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {


    // ClienteMapper mapper = Mappers.getMapper(ClienteMapper.class);

    ClienteDto toDto(Cliente cliente); //Recibe un Cliente y devuelve un ClienteDto
    Cliente toEntity(ClienteDto clienteDto);//Recibe un ClienteDto y devuelve un Cliente

    List<ClienteDto> toDtoList(List<Cliente> clientes);
    List<Cliente> toEntityList(List<ClienteDto> dtos);

}
