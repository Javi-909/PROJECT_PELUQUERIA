package com.example.demo.service.implementacion;

import com.example.demo.dto.ServicioDto;
import com.example.demo.dto.ServicioPeluDto;
import com.example.demo.entity.Servicio;

import com.example.demo.entity.ServicioPelu;
import com.example.demo.mapper.ServicioMapper;
import com.example.demo.repository.servicioPeluRepository;
import com.example.demo.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.repository.servicioRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private servicioRepository servicioRepository1;
    private servicioPeluRepository servicioPeluRepository1;

    @Autowired
    ServicioMapper servicioMapper;


    //LISTAR TODOS LOS SERVICIOS
    @Override
    public List<ServicioDto> findAll() {
        List<Servicio> servicio = servicioRepository1.findAll();
        return servicio.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    //CREAR SERVICIO (GENÉRICO)
    @Override
    public ServicioDto createServicio(ServicioDto servicioDto) { //crear servicio genérico (corte barba, teñir..)
        Servicio servicio = this.toEntity(servicioDto);
        Servicio saved = servicioRepository1.save(servicio);
        return this.toDto(saved);
    }


    //MOSTRAR SERVICIO POR ID
    @Override
    public ResponseEntity<ServicioDto> mostrarServicioPorId(Integer id) {
        return servicioRepository1.findById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    //ELIMINAR SERVICIO
    @Override
    public void deleteServicio(Integer id) {
        servicioRepository1.deleteById(id);
    }


    //METODOS PRIVADOS

    private ServicioDto toDto(Servicio servicio) {  //sirve para hacer el mappeo natural (sin MapStruct) entre servicio y servicioDto
        if (servicio == null) return null;
        ServicioDto dto = new ServicioDto();
        dto.setNombre(servicio.getNombre());
        dto.setDescripcion(servicio.getDescripcion());
        return dto;
    }

    private ServicioPeluDto toDto(ServicioPelu servicioPelu) {  //sirve para hacer el mappeo natural (sin MapStruct) entre servicioPelu y servicioPeluDto
        if (servicioPelu == null) return null;
        ServicioPeluDto dto = new ServicioPeluDto();
        dto.setPrecio(servicioPelu.getPrecio());
        dto.setDuracion(servicioPelu.getDuracion());
        return dto;
    }

    private Servicio toEntity(ServicioDto servicioDto) {
        if (servicioDto == null) return null;
        Servicio servicio = new Servicio();
        servicio.setNombre(servicioDto.getNombre());
        servicio.setDescripcion(servicioDto.getDescripcion());
        return servicio;
    }

}



