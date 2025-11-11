package com.example.demo.mapper;


import com.example.demo.dto.ReservaClienteDto;
import com.example.demo.entity.ReservaCliente;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservaClienteMapper {

    ReservaClienteDto toDto(ReservaCliente reservaCliente);
    ReservaCliente toEntity(ReservaClienteDto reservaClienteDto);

    List<ReservaClienteDto> toDtoList(List<ReservaCliente> reservaClientes);
    List<ReservaCliente> toEntityList(List<ReservaClienteDto> dtos);


}
