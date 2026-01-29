package com.example.demo.service.implementacion;


import com.example.demo.dto.PeluqueriaDto;
import com.example.demo.dto.ServicioResponseDto;
import com.example.demo.dto.HorarioDto;
import com.example.demo.entity.Horario;
import com.example.demo.entity.Peluqueria;
import com.example.demo.mapper.HorarioMapper;
import com.example.demo.mapper.PeluqueriaMapper;
import com.example.demo.mapper.ServicioPeluMapper;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeluqueriaServiceImpl implements PeluqueriaService {

    @Autowired
    private final peluqueriaRepository peluqueriaRepository1;
    @Autowired
    private final servicioRepository servicioRepository1;
    @Autowired
    private final servicioPeluRepository servicioPeluRepository1;
    @Autowired
    private final horarioRepository horarioRepository1;

    private final PeluqueriaMapper peluqueriaMapper;
    private HorarioMapper horarioMapper;
    private ServicioPeluMapper servicioPeluMapper;

    public PeluqueriaServiceImpl(PeluqueriaMapper peluqueriaMapper, horarioRepository horarioRepository1,
                                 servicioRepository servicioRepository1, servicioPeluRepository servicioPeluRepository1, peluqueriaRepository peluqueriaRepository1, HorarioMapper horarioMapper,
                                 ServicioPeluMapper servicioPeluMapper){
        this.horarioRepository1 = horarioRepository1;
        this.peluqueriaMapper = peluqueriaMapper;
        this.peluqueriaRepository1 = peluqueriaRepository1;
        this.servicioPeluRepository1 = servicioPeluRepository1;
        this.servicioRepository1 = servicioRepository1;
        this.horarioMapper = horarioMapper;
        this.servicioPeluMapper = servicioPeluMapper;
    }


    @Override
    public List<PeluqueriaDto> findAll() {
        List<Peluqueria> peluqueria = peluqueriaRepository1.findAll();
        return peluqueria.stream()
                .map(peluqueriaMapper::toDto)
                .collect(Collectors.toList());
    }


    //CREAR PELUQUERIA
    @Override
    public PeluqueriaDto createPeluqueria(PeluqueriaDto peluqueriaDto) {
        Peluqueria peluqueria = peluqueriaMapper.toEntity(peluqueriaDto);
        peluqueria.setId(null); //para que funcione en el front Añadir Servicio a una peluqueria
        Peluqueria saved = peluqueriaRepository1.save(peluqueria); //con el metodo save, solo puedo guardar entitys (pq es base de datos)
        return peluqueriaMapper.toDto(saved);
    }

    //MOSTRAR PELUQUERIA
    @Override
    public ResponseEntity<PeluqueriaDto> mostrarPeluqueriaPorId(Integer id) {
        return peluqueriaRepository1.findById(id)
                .map(peluqueriaMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //VER SERVICIOS DE UNA PELUQUERIA
    @Override
    public ResponseEntity<List<ServicioResponseDto>> listarServiciosPorPeluqueria(Integer peluqueriaId) {
        List<ServicioJoinProjection> rows = servicioPeluRepository1.findServiciosByPeluqueriaId(peluqueriaId);

        List<ServicioResponseDto> dtos = rows.stream()
                .map(sp -> new ServicioResponseDto(
                        sp.getId(),
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
        if(peluqueriaRepository1.existsById(peluqueriaId)){
            throw new RuntimeException("La peluqueria con id:" + peluqueriaId +"ya tiene un horario establecido");
        }
        Horario horario = this.toEntity(horarioDto,peluqueriaId);
        //Hacer mapeo privado con horario, ya que se le pasa como parametro
        //peluqueriaId, por lo que con mapStruct no serviria
        Horario saved = horarioRepository1.save(horario);
        return ResponseEntity.ok(toDto(saved));
    }

    //ELIMINAR HORARIO
    public void deleteHorario(Integer id){
        horarioRepository1.deleteById(id);
    }


    //ACTUALIZAR HORARIO
    @Transactional
    public ResponseEntity<HorarioDto> actualizarHorario(Integer id, HorarioDto horarioDto) {

         if(!horarioRepository1.existsById(id)){
             throw new RuntimeException("Horario con id " + id + " no existe");
         }

         if (horarioDto == null) {
                return ResponseEntity.badRequest().build();
            }
        return horarioRepository1.findById(id)
            .map(horario -> {
                // Se actualiza los campos que son no-NULL

                if (horarioDto.getHora_apertura() != null) {
                    horario.setHoraApertura(horarioDto.getHora_apertura());
                }
                if (horarioDto.getHora_cierre() != null) {
                    horario.setHoraCierre(horarioDto.getHora_cierre());
                }
                if (horarioDto.getDia_semana() != null) {
                    horario.setDiaSemana(horarioDto.getDia_semana());
                }

                Horario saved = horarioRepository1.save(horario);
                return ResponseEntity.ok(toDto(saved));
            })
            .orElse(ResponseEntity.notFound().build());
    }



    //METODOS PRIVADOS


    private Horario toEntity(HorarioDto horarioDto,Integer peluqueriaId) {  //sirve para hacer el mappeo natural (sin MapStruct)
        if (horarioDto == null) return null;
        Horario horario = new Horario();
        horario.setPeluqueriaId(peluqueriaId);
        horario.setHoraApertura(horarioDto.getHora_apertura());
        horario.setHoraCierre(horarioDto.getHora_cierre());
        horario.setDiaSemana(horarioDto.getDia_semana());
        return horario;
    }

    private HorarioDto toDto(Horario horario) {  //sirve para hacer el mappeo natural (sin MapStruct)
        if (horario == null) return null;
        HorarioDto horarioDto = new HorarioDto();
        horarioDto.setHora_apertura(horario.getHoraApertura());
        horarioDto.setHora_cierre(horario.getHoraCierre());
        horarioDto.setDia_semana(horario.getDiaSemana());
        return horarioDto;
    }

}



