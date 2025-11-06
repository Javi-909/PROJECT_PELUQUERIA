package com.example.demo.service.implementacion;


import com.example.demo.dto.ServicioPeluCreacionDto;
import com.example.demo.dto.ServicioPeluDto;
import com.example.demo.entity.ServicioPelu;
import com.example.demo.mapper.ServicioPeluMapper;
import com.example.demo.repository.peluqueriaRepository;
import com.example.demo.repository.servicioPeluRepository;
import com.example.demo.service.ServicioPeluService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.servicioRepository;


import java.util.List;
import java.util.stream.Collectors;
@Service
public class ServicioPeluServiceImpl implements ServicioPeluService {

    @Autowired
    private servicioPeluRepository servicioPeluRepository1;
    @Autowired
    private servicioRepository servicioRepository1;
    @Autowired
    private peluqueriaRepository peluqueriaRepository1;

    private ServicioPeluMapper servicioPeluMapper;


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
    public ServicioPeluDto añadirServicioApeluqueria(ServicioPeluCreacionDto request) {

        Integer servicioId = request.getServicioId();
        Integer peluqueriaId = request.getPeluqueriaId();
        Integer precio = request.getPrecio();
        Integer duracion = request.getDuracion();

        if(!servicioRepository1.existsById(servicioId)){
            throw new RuntimeException("Servicio no encontrado con id: " + servicioId);
        }
        if(!peluqueriaRepository1.existsById(peluqueriaId)){
            throw new RuntimeException("Peluqueria no encontrada con id: " + peluqueriaId);
        }

        ServicioPelu servicioPelu = new ServicioPelu();
        servicioPelu.setServicioId(servicioId);
        servicioPelu.setPeluqueria_id(peluqueriaId);
        servicioPelu.setDuracion(duracion);
        servicioPelu.setPrecio(precio);

        ServicioPelu saved = servicioPeluRepository1.save(servicioPelu);
        ServicioPeluDto dto = servicioPeluMapper.toDto(saved);
        return dto;
    }

}



