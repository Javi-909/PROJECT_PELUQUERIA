package com.example.demo.service.implementacion;

import com.example.demo.dto.ReservaClienteDto;
import com.example.demo.dto.ReservaDto;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.EstadoReserva;
import com.example.demo.entity.Reserva;
import com.example.demo.entity.ReservaCliente;
import com.example.demo.mapper.ReservaClienteMapper;
import com.example.demo.mapper.ReservaMapper;
import com.example.demo.repository.clienteRepository;
import com.example.demo.repository.reservaClienteRepository;
import com.example.demo.repository.reservaRepository;
import com.example.demo.service.ReservaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
        @Autowired
        private ReservaMapper reservaMapper;

        @Autowired
        private ReservaClienteMapper reservaClienteMapper;

        private EstadoReserva estadoReserva;

        public ReservaServiceImpl(ReservaMapper reservaMapper, reservaClienteRepository reservaClienteRepository1, reservaRepository reservaRepository1, clienteRepository clienteRepository1, ReservaClienteMapper reservaClienteMapper){
            this.reservaMapper = reservaMapper;
            this.clienteRepository1 = clienteRepository1;
            this.reservaRepository1 = reservaRepository1;
            this.reservaClienteRepository1 = reservaClienteRepository1;
            this.reservaClienteMapper = reservaClienteMapper;
        }


        //CREAR RESERVA MEDIANTE ID DE CLIENTE
        @Override
        @Transactional
            public ReservaDto createReserva(Integer clienteId, ReservaDto dto) {

            if(dto == null || dto.getFecha() == null || dto.getHora() == null){
                throw new IllegalArgumentException("fecha y hora requeridas");
            }


            //vemos si el cliente existe
            Cliente cliente = clienteRepository1.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            // Comprobamos que la fecha solo sea futura (no puede ser fecha anterior)
            LocalDate fecha_hoy = LocalDate.now();
            if(dto.getFecha().isBefore(fecha_hoy)) {
                throw new IllegalArgumentException("La fecha no puede ser pasada");
            }

            // Vemos que exista la reserva (es decir que alguien haya reservado)
            boolean exists = !reservaRepository1.findByIdServicioPeluAndFechaAndHora(dto.getIdServicioPelu(), dto.getFecha(), dto.getHora()).isEmpty();
            if (exists) {
                throw new RuntimeException("El horario ya está reservado para ese servicio");
            }

            if(dto.getEstado() == null){ //si no ponemos nada en estado se pone PENDIENTE automatico
                dto.setEstado(EstadoReserva.PENDIENTE);
            }

            Reserva reserva = reservaMapper.toEntity(dto);
            //guardamos la entity y pasamos a dto
            Reserva saved = reservaRepository1.save(reserva);

            //Para enlazar cliente y reserva en la tabla reservaCliente:
            ReservaCliente mapping = new ReservaCliente();
            mapping.setCliente_id(clienteId);
            mapping.setReserva_id(saved.getId());
            reservaClienteRepository1.save(mapping);

            return reservaMapper.toDto(saved);

        }

        //MOSTRAR RESERVAS DE UN CLIENTE
        @Override
        public List<ReservaClienteDto> findByClienteId(Integer clienteId){
            return reservaClienteRepository1.findByClienteId(clienteId) //devuelve una entity de Reserva
                    .stream().map(reservaClienteMapper::toDto).collect(Collectors.toList());

        }

    /*
        public void cancelaReserva(Integer clienteId, Integer reservaId){

            List<ReservaCliente> reservas = reservaClienteRepository1.findByReserva_id(reservaId);


        }

     */





    }


