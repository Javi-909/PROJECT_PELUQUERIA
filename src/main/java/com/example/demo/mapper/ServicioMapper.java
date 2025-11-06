package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServicioMapper {

    //ServicioMapper mapper = Mappers.getMapper(ServicioMapper.class);

    ServicioDto toDto(Servicio servicio);
    Servicio toEntity(ServicioDto servicioDto);

    List<ServicioDto> toDtoList(List<Servicio> servicios);
    List<Servicio> toEntityList(List<ServicioDto> dtos);


}
