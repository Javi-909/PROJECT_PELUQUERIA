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
    @Autowired
    private servicioPeluRepository servicioPeluRepository1;

    @Autowired
    ServicioMapper servicioMapper;

    public ServicioServiceImpl(servicioRepository servicioRepository1, servicioPeluRepository servicioPeluRepository1, ServicioMapper servicioMapper){
        this.servicioMapper = servicioMapper;
        this.servicioPeluRepository1 = servicioPeluRepository1;
        this.servicioRepository1 = servicioRepository1;
    }


    //LISTAR TODOS LOS SERVICIOS
    @Override
    public List<ServicioDto> findAll() {
        List<Servicio> servicio = servicioRepository1.findAll();
        return servicio.stream()
                .map(servicioMapper::toDto)
                .collect(Collectors.toList());
    }

    //CREAR SERVICIO (GENÉRICO)
    @Override
    public ServicioDto createServicio(ServicioDto servicioDto) { //crear servicio genérico (corte barba, teñir..)
        Servicio servicio = servicioMapper.toEntity(servicioDto);
        Servicio saved = servicioRepository1.save(servicio);
        return servicioMapper.toDto(saved);
    }


    //MOSTRAR SERVICIO POR ID
    @Override
    public ResponseEntity<ServicioDto> mostrarServicioPorId(Integer id) {
        return servicioRepository1.findById(id)
                .map(servicioMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    //ELIMINAR SERVICIO
    @Override
    public void deleteServicio(Integer id) {
        servicioRepository1.deleteById(id);
    }

}



