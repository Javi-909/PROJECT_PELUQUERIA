package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PeluqueriaMapper {

    //PeluqueriaMapper mapper = Mappers.getMapper(PeluqueriaMapper.class);

    PeluqueriaDto toDto(Peluqueria peluqueria);
    Peluqueria toEntity(PeluqueriaDto peluqueriaDto);


}