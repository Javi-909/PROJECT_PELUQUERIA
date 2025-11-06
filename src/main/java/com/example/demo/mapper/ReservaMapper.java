package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    ReservaMapper mapper = Mappers.getMapper(ReservaMapper.class);

    @Mapping(source="fecha", target = "fecha")
    @Mapping(source = "hora", target = "hora")
    ReservaDto toDto(Reserva reserva);

    Reserva toEntity(ReservaDto reservaDto);


}