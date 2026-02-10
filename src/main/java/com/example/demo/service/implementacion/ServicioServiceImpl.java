package com.example.demo.service.implementacion;

import com.example.demo.dto.ServicioDto;
import com.example.demo.entity.Servicio;
import com.example.demo.mapper.ServicioMapper;
import com.example.demo.service.ServicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.repository.servicioRepository;
import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {

    // Inyección por constructor (mejor que Autowired)
    private final servicioRepository servicioRepository1;
    private final ServicioMapper servicioMapper;

    // Constructor con todas las dependencias
    public ServicioServiceImpl(servicioRepository servicioRepository1, ServicioMapper servicioMapper){
        this.servicioMapper = servicioMapper;
        this.servicioRepository1 = servicioRepository1;
    }


    //LISTAR TODOS LOS SERVICIOS
    @Override
    public List<ServicioDto> findAll() {
        List<Servicio> servicio = servicioRepository1.findAll();
        return servicio.stream()
                .map(servicioMapper::toDto)
                .toList();
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
