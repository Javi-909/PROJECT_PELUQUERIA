package com.example.demo.service.implementacion;


import com.example.demo.dto.*;
import com.example.demo.entity.Horario;
import com.example.demo.entity.Peluqueria;
import com.example.demo.entity.Servicio;
import com.example.demo.entity.ServicioPelu;
import com.example.demo.mapper.PeluqueriaMapper;
import com.example.demo.repository.horarioRepository;
import com.example.demo.repository.projection.ServicioJoinProjection;
import com.example.demo.repository.servicioPeluRepository;
import com.example.demo.repository.servicioRepository;
import com.example.demo.service.PeluqueriaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.repository.peluqueriaRepository;


import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
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
    private horarioRepository horarioRepository1;

    @Override
    public List<PeluqueriaDto> findAll() {
        List<Peluqueria> peluqueria = peluqueriaRepository1.findAll();
        return peluqueria.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    //CREAR PELUQUERIA
    @Override
    public PeluqueriaDto createPeluqueria(PeluqueriaDto peluqueriaDto) {
        Peluqueria peluqueria = this.toEntity(peluqueriaDto);
        Peluqueria saved = peluqueriaRepository1.save(peluqueria); //con el metodo save, solo puedo guardar entitys (pq es base de datos)
        return this.toDto(saved);
    }

    //MOSTRAR PELUQUERIA
    @Override
    public ResponseEntity<PeluqueriaDto> mostrarPeluqueriaPorId(Integer id) {
        return peluqueriaRepository1.findById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //VER SERVICIOS DE UNA PELUQUERIA
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

    //ELIMINAR PELUQUERIA
    public void deletePeluqueria(Integer id){
        peluqueriaRepository1.deleteById(id);
    }

    //CREAR HORARIO PARA UNA PELUQUERIA
    public ResponseEntity<HorarioDto> createHorario(Integer peluqueriaId, HorarioDto horarioDto){
        if(!peluqueriaRepository1.existsById(peluqueriaId)){
            throw new RuntimeException("No existe la peluqueria con id:" + peluqueriaId);
        }
        Horario horario = toEntity(horarioDto,peluqueriaId);
        Horario saved = horarioRepository1.save(horario);
        return ResponseEntity.ok(toDto(saved));
    }

    //ELIMINAR HORARIO (hay que añadir en el body id y su value, en Postman)
    public void deleteHorario(Integer id){
        horarioRepository1.deleteById(id);
    }


    //ACTUALIZAR HORARIO
    @Transactional
    public ResponseEntity<HorarioDto> actualizarHorario(Integer id, HorarioDto horarioDto) {

     if (horarioDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return horarioRepository1.findById(id)
            .map(horario -> {
                // Se actualiza los campos que son no-NULL

                if (horarioDto.getHoraApertura() != null) {
                    horario.setHoraApertura(horarioDto.getHoraApertura());
                }
                if (horarioDto.getHoraCierre() != null) {
                    horario.setHoraCierre(horarioDto.getHoraCierre());
                }
                if (horarioDto.getDiaSemana() != null) {
                    horario.setDiaSemana(horarioDto.getDiaSemana());
                }

                Horario saved = horarioRepository1.save(horario);
                return ResponseEntity.ok(toDto(saved));
            })
            .orElse(ResponseEntity.notFound().build());
    }



    //METODOS PRIVADOS

    private PeluqueriaDto toDto(Peluqueria peluqueria) {  //sirve para hacer el mappeo natural (sin MapStruct)
        if (peluqueria == null) return null;
        PeluqueriaDto dto = new PeluqueriaDto();
        dto.setNombre(peluqueria.getNombre());
        dto.setEmail(peluqueria.getEmail());
        dto.setDireccion(peluqueria.getDireccion());
        dto.setTelefono(peluqueria.getTelefono());
        return dto;
    }
    private Peluqueria toEntity(PeluqueriaDto peluqueriaDto) {  //sirve para hacer el mappeo natural (sin MapStruct)
        if (peluqueriaDto == null) return null;
        Peluqueria peluqueria = new Peluqueria();
        peluqueria.setNombre(peluqueriaDto.getNombre());
        peluqueria.setEmail(peluqueriaDto.getEmail());
        peluqueria.setDireccion(peluqueriaDto.getDireccion());
        peluqueria.setTelefono(peluqueriaDto.getTelefono());
        return peluqueria;
    }

    private ServicioPeluDto toDto(ServicioPelu servicioPelu) {  //sirve para hacer el mappeo natural (sin MapStruct) entre servicioPelu y servicioPeluDto
        if (servicioPelu == null) return null;
        ServicioPeluDto dto = new ServicioPeluDto();
        dto.setPrecio(servicioPelu.getPrecio());
        dto.setDuracion(servicioPelu.getDuracion());
        return dto;
    }
    private Horario toEntity(HorarioDto horarioDto,Integer peluqueriaId) {  //sirve para hacer el mappeo natural (sin MapStruct)
        if (horarioDto == null) return null;
        Horario horario = new Horario();
        horario.setPeluqueriaId(peluqueriaId);
        horario.setHoraApertura(horarioDto.getHoraApertura());
        horario.setHoraCierre(horarioDto.getHoraCierre());
        horario.setDiaSemana(horarioDto.getDiaSemana());
        return horario;
    }

    private HorarioDto toDto(Horario horario) {  //sirve para hacer el mappeo natural (sin MapStruct)
        if (horario == null) return null;
        HorarioDto horarioDto = new HorarioDto();
        horarioDto.setHoraApertura(horario.getHoraApertura());
        horarioDto.setHoraCierre(horarioDto.getHoraCierre());
        horario.setDiaSemana(horarioDto.getDiaSemana());
        return horarioDto;
    }

}



