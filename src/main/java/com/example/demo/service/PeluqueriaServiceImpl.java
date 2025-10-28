package com.example.demo.service;


import com.example.demo.dto.PeluqueriaDto;
import com.example.demo.dto.ServicioPeluDto;
import com.example.demo.dto.ServicioResponseDto;
import com.example.demo.entity.Peluqueria;
import com.example.demo.entity.Servicio;
import com.example.demo.entity.ServicioPelu;
import com.example.demo.mapper.PeluqueriaMapper;
import com.example.demo.repository.projection.ServicioJoinProjection;
import com.example.demo.repository.servicioPeluRepository;
import com.example.demo.repository.servicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.repository.peluqueriaRepository;


import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class PeluqueriaServiceImpl implements PeluqueriaService {

    @Autowired
    private peluqueriaRepository peluqueriaRepository1;
    @Autowired
    private servicioRepository servicioRepository1;
    @Autowired
    private servicioPeluRepository servicioPeluRepository1;

    @Autowired
    PeluqueriaMapper peluqueriaMapper;

    @Override
    public List<PeluqueriaDto> findAll() {
        List<Peluqueria> peluqueria = peluqueriaRepository1.findAll();
        return peluqueria.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public PeluqueriaDto createPeluqueria(PeluqueriaDto peluqueriaDto) {
        Peluqueria peluqueria = peluqueriaMapper.toEntity(peluqueriaDto);
        Peluqueria saved = peluqueriaRepository1.save(peluqueria); //con el metodo save, solo puedo guardar entitys (pq es base de datos)
        return peluqueriaMapper.toDto(saved);
    }
    @Override
    public ResponseEntity<PeluqueriaDto> mostrarPeluqueriaPorId(Integer id) {
        return peluqueriaRepository1.findById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    private PeluqueriaDto toDto(Peluqueria peluqueria) {  //sirve para hacer el mappeo natural (sin MapStruct)
        if (peluqueria == null) return null;
        PeluqueriaDto dto = new PeluqueriaDto();
        dto.setNombre(peluqueria.getNombre());
        dto.setEmail(peluqueria.getEmail());
        dto.setDireccion(peluqueria.getDireccion());
        dto.setTelefono(peluqueria.getTelefono());
        return dto;
    }

    private ServicioPeluDto toDto(ServicioPelu servicioPelu) {  //sirve para hacer el mappeo natural (sin MapStruct) entre servicioPelu y servicioPeluDto
        if (servicioPelu == null) return null;
        ServicioPeluDto dto = new ServicioPeluDto();
        dto.setPrecio(servicioPelu.getPrecio());
        dto.setDuracion(servicioPelu.getDuracion());
        return dto;
    }
    @Override
    public ResponseEntity<List<ServicioResponseDto>> listarServiciosPorPeluqueria(Integer peluqueriaId) {
        List<ServicioJoinProjection> rows = servicioPeluRepository1.findServiciosByPeluqueriaId(peluqueriaId);

        List<ServicioResponseDto> dtos = rows.stream()
                .map(sp -> new ServicioResponseDto(
                        sp.getNombre(),
                        sp.getDescripcion(),
                        sp.getPrecio(),
                        sp.getDuracion()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}


//@Override
//public void deletePeluqueria(String id) {
  //  peluqueriaRepository.deleteById(id);
//}

