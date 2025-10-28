package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    // ClienteMapper mapper = Mappers.getMapper(ClienteMapper.class);

    ClienteDto toDto(Cliente cliente); //Recibe un Cliente y devuelve un ClienteDto
    Cliente toEntity(ClienteDto clienteDto); //Recibe un ClienteDto y devuelve un Cliente


}
