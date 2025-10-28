package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HorarioMapper {

    //HorarioMapper mapper = Mappers.getMapper(HorarioMapper.class);

    HorarioDto toDto(Horario horario);
    Horario toEntity(HorarioDto horarioDto);


}
