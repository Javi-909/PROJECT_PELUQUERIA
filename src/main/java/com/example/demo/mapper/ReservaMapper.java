package com.example.demo.mapper;

import com.example.demo.dto.ReservaDto;
import com.example.demo.entity.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;



@Mapper(componentModel = "spring")
public interface ReservaMapper {

    ReservaMapper mapper = Mappers.getMapper(ReservaMapper.class);

    @Mapping(source="fecha", target = "fecha")
    @Mapping(source = "hora", target = "hora")
    @Mapping(source = "estado", target = "estado")
    ReservaDto toDto(Reserva reserva);

    @Mapping(source = "estado", target = "estado")
    Reserva toEntity(ReservaDto reservaDto);


}