package com.example.demo.service.implementacion;


import com.example.demo.dto.ServicioPeluCreacionDto;
import com.example.demo.dto.ServicioPeluDto;
import com.example.demo.entity.ServicioPelu;
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
        ServicioPeluDto dto = this.toDto(saved);
        return dto;
    }


    private ServicioPeluDto toDto(ServicioPelu servicioPelu) {  //sirve para hacer el mappeo natural (sin MapStruct) entre servicioPelu y servicioPeluDto
        if (servicioPelu == null) return null;
        ServicioPeluDto dto = new ServicioPeluDto();
        dto.setPrecio(servicioPelu.getPrecio());
        dto.setDuracion(servicioPelu.getDuracion());
        return dto;
    }

}



