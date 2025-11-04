package com.example.demo.service.implementacion;

import com.example.demo.dto.ReservaDto;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.EstadoReserva;
import com.example.demo.entity.Reserva;
import com.example.demo.entity.ReservaCliente;
import com.example.demo.repository.clienteRepository;
import com.example.demo.repository.reservaClienteRepository;
import com.example.demo.repository.reservaRepository;
import com.example.demo.service.ReservaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


    @Service
    public class ReservaServiceImpl implements ReservaService{

        @Autowired
        private reservaRepository reservaRepository1;
        @Autowired
        private clienteRepository clienteRepository1;
        @Autowired
        private reservaClienteRepository reservaClienteRepository1;


        //CREAR RESERVA MEDIANTE ID DE CLIENTE
        @Override
        @Transactional
            public ReservaDto createReserva(Integer clienteId, ReservaDto dto) {

            //vemos si el cliente existe
            Cliente cliente = clienteRepository1.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            // Comprobamos que la fecha solo sea futura (no puede ser fecha anterior)
            Date fecha_hoy = new Date();
            if(dto.getFecha().before(fecha_hoy)) {
                throw new IllegalArgumentException("La fecha no puede ser pasada");
            }

            // Vemos que exista la reserva (es decir que alguien haya reservado)
            boolean exists = !reservaRepository1.findByIdServicioPeluAndFechaAndHora(dto.getIdServicioPelu(), dto.getFecha(), dto.getHora()).isEmpty();
            if (exists) {
                throw new RuntimeException("El horario ya está reservado para ese servicio");
            }

            Reserva reserva = toEntity(dto);
            //guardamos la entity y pasamos a dto
            Reserva saved = reservaRepository1.save(reserva);

            //Para enlazar cliente y reserva en la tabla reservaCliente:
            ReservaCliente mapping = new ReservaCliente();
            mapping.setCliente_id(clienteId);
            mapping.setReserva_id(saved.getId());
            reservaClienteRepository1.save(mapping);

            return toDto(saved);

        }

        //MOSTRAR RESERVAS DE UN CLIENTE
        @Override
        public List<ReservaDto> findByClienteId(Integer clienteId){
            return reservaRepository1.findByClienteId(clienteId) //devuelve una entity de Reserva
                    .stream().map(this::toDto).collect(Collectors.toList());

        }


       // public void cancelReserva(Integer clienteId, Integer reservaId){


        //}


        //METODOS PRIVADOS
        private ReservaDto toDto(Reserva reserva) {  //sirve para hacer el mappeo natural (sin MapStruct) entre servicio y servicioDto
            if (reserva == null) return null;
            ReservaDto dto = new ReservaDto();
            dto.setFecha(reserva.getFecha());
            dto.setHora(reserva.getHora());
            //dto.setEstado(EstadoReserva.PENDIENTE);
            dto.setIdServicioPelu(reserva.getIdServicioPelu());
            return dto;
        }

        private Reserva toEntity(ReservaDto dto){
            if(dto == null) return null;
            Reserva reserva = new Reserva();
            //reserva.setEstado(dto.getEstado());
            reserva.setFecha(dto.getFecha());
            reserva.setHora(dto.getHora());
            reserva.setIdServicioPelu(dto.getIdServicioPelu());
            return reserva;
        }

    }


