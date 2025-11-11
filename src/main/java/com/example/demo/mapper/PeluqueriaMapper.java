package com.example.demo.mapper;

import com.example.demo.dto.PeluqueriaDto;
import com.example.demo.entity.Peluqueria;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PeluqueriaMapper {

    //PeluqueriaMapper mapper = Mappers.getMapper(PeluqueriaMapper.class);

    PeluqueriaDto toDto(Peluqueria peluqueria);
    Peluqueria toEntity(PeluqueriaDto peluqueriaDto);

    List<PeluqueriaDto> toDtoList(List<Peluqueria> peluquerias);
    List<Peluqueria> toEntityList(List<PeluqueriaDto> dtos);


}