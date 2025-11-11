package com.example.demo.mapper;

import com.example.demo.dto.ServicioPeluDto;
import com.example.demo.entity.ServicioPelu;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ServicioPeluMapper {

    //ServicioPeluMapper mapper = Mappers.getMapper(ServicioPeluMapper.class);

    ServicioPeluDto toDto(ServicioPelu servicioPelu);
    ServicioPelu toEntity(ServicioPeluDto servicioPeluDto);


}