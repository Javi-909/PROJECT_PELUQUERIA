package com.example.demo.mapper;

import com.example.demo.dto.HorarioDto;
import com.example.demo.entity.Horario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HorarioMapper {

    HorarioMapper mapper = Mappers.getMapper(HorarioMapper.class);

    //@Mapping(source="hora_apertura", target = "hora_apertura")
    HorarioDto toDto(Horario horario);

    //@Mapping(source="hora_apertura", target = "hora_apertura")
    Horario toEntity(HorarioDto horarioDto);
    List<HorarioDto> toDtoList(List<Horario> horarios);
    List<Horario> toEntityList(List<HorarioDto> dtos);


}
