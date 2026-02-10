package com.example.demo.service.implementacion;


import com.example.demo.dto.ServicioPeluCreacionDto;
import com.example.demo.dto.ServicioPeluDto;
import com.example.demo.entity.ServicioPelu;
import com.example.demo.mapper.ServicioPeluMapper;
import com.example.demo.repository.peluqueriaRepository;
import com.example.demo.repository.servicioPeluRepository;
import com.example.demo.service.ServicioPeluService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.example.demo.repository.servicioRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Objects;


@Service
public class ServicioPeluServiceImpl implements ServicioPeluService {

    private final servicioPeluRepository servicioPeluRepository1;
    private final servicioRepository servicioRepository1;
    private final peluqueriaRepository peluqueriaRepository1;

    private final ServicioPeluMapper servicioPeluMapper;


    public ServicioPeluServiceImpl(servicioPeluRepository servicioPeluRepository1, servicioRepository servicioRepository1, peluqueriaRepository peluqueriaRepository1,
                                   ServicioPeluMapper servicioPeluMapper){
        this.servicioPeluMapper = servicioPeluMapper;
        this.servicioPeluRepository1 = servicioPeluRepository1;
        this.peluqueriaRepository1 = peluqueriaRepository1;
        this.servicioRepository1 = servicioRepository1;
    }

    //AÑADIR UN SERVICIO A UNA PELUQUERIA
    @Override
    @Transactional
    public ServicioPeluDto addServicioToPeluqueria(ServicioPeluCreacionDto request) {

        Objects.requireNonNull(request, "request es requerido");

        Integer servicioId = request.getServicioId();
        Integer peluqueriaId = request.getPeluqueriaId();
        Integer precio = request.getPrecio();
        Integer duracion = request.getDuracion();

        Objects.requireNonNull(servicioId, "servicioId es requerido");
        Objects.requireNonNull(peluqueriaId, "peluqueriaId es requerido");

        if(!servicioRepository1.existsById(servicioId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado con id: " + servicioId);
        }
        if(!peluqueriaRepository1.existsById(peluqueriaId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Peluqueria no encontrada con id: " + peluqueriaId);
        }

        ServicioPelu servicioPelu = new ServicioPelu();
        servicioPelu.setServicioId(servicioId);
        servicioPelu.setPeluqueria_id(peluqueriaId);
        servicioPelu.setDuracion(duracion);
        servicioPelu.setPrecio(precio);

        ServicioPelu saved = servicioPeluRepository1.save(servicioPelu);
        return servicioPeluMapper.toDto(saved);
    }

}
