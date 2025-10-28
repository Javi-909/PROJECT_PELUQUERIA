package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServicioPeluMapper {

    //ServicioPeluMapper mapper = Mappers.getMapper(ServicioPeluMapper.class);

    ServicioPeluDto toDto(ServicioPelu servicioPelu);
    ServicioPelu toEntity(ServicioPeluDto servicioPeluDto);


}