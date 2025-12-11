package com.example.demo.service.implementacion;

import com.example.demo.dto.ReservaClienteDto;
import com.example.demo.dto.ReservaDto;
import com.example.demo.dto.ReservaNegocioDto;
import com.example.demo.entity.*;
import com.example.demo.mapper.ReservaClienteMapper;
import com.example.demo.mapper.ReservaMapper;
import com.example.demo.repository.*;
import com.example.demo.service.ReservaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import  org.springframework.http.HttpStatus;

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
        private servicioPeluRepository servicioPeluRepository1;
        @Autowired
        private servicioRepository servicioRepository1;

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
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El horario ya está reservado para ese servicio");
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

        //CANCELAR UNA RESERVA
        @Transactional
        @Override
        public ResponseEntity<ReservaDto> cancelaReserva(Integer reservaId){
                return reservaRepository1.findById(reservaId)
                        .map(reserva -> { //pasamos de OPTIONAL a ENTITY

                            reserva.setEstado(EstadoReserva.CANCELADA); //cambiamos estado a CANCELADA

                            Reserva saved = reservaRepository1.save(reserva);

                            //Eliminamos de la tabla reservacliente
                            reservaClienteRepository1.deleteByReservaId(reservaId);

                            // Map to DTO and return
                            ReservaDto dto = reservaMapper.toDto(saved);
                            System.out.println("Reserva con id: " + reservaId + " cancelada");
                            return ResponseEntity.ok(dto);
                        })
                        .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @Override
        public List<ReservaNegocioDto> getReservasDePeluqueria(Integer peluqueriaId){
            List<Reserva> reservas = reservaRepository1.findByPeluqueriaId(peluqueriaId);
            return reservas.stream().map(reserva -> {

                // Valores por defecto
                String nombreCliente = "Desconocido";
                String emailCliente = "-";
                String nombreServicio = "Servicio General";
                Integer precio = 0;

                //BUSCAR EL CLIENTE
                List<ReservaCliente> rcs = reservaClienteRepository1.findByReserva_id(reserva.getId());
                ReservaCliente rc = null;

                if(rcs!= null && !rcs.isEmpty()){
                    rc = rcs.get(0);
                }

                if(rc != null){ //Si encontramos la relación, buscamos los datos personales del cliente que tiene la reserva
                    Cliente cliente = clienteRepository1.findById(rc.getCliente_id()).orElse(null);
                    if(cliente != null) {
                        nombreCliente = cliente.getNombre();
                        emailCliente = cliente.getEmail();
                    }
                }

                //BUSCAR EL SERVICIO
                if(reserva.getIdServicioPelu() != null){
                    ServicioPelu sp = servicioPeluRepository1.findById(reserva.getIdServicioPelu()).orElse(null);
                    if(sp != null){
                        precio = sp.getPrecio();
                        Servicio s = servicioRepository1.findById(sp.getServicioId()).orElse(null);
                        if(s != null){
                            nombreServicio = s.getNombre();
                        }
                    }
                }
                //DEVOLVEMOS EL DTO (que es lo que el negocio va a ver)
                return new ReservaNegocioDto(
                        reserva.getId(),
                        reserva.getFecha(),
                        reserva.getHora(),
                        nombreCliente,
                        emailCliente,
                        nombreServicio,
                        precio
                );
            }).collect(Collectors.toList());
        }


    }


