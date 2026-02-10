package com.example.demo.service.implementacion;

import com.example.demo.dto.ReservaClienteDto;
import com.example.demo.dto.ReservaDto;
import com.example.demo.dto.ReservaNegocioDto;
import com.example.demo.entity.*;
import com.example.demo.mapper.ReservaClienteMapper;
import com.example.demo.mapper.ReservaMapper;
import com.example.demo.repository.*;
import com.example.demo.service.EmailService;
import com.example.demo.service.ReservaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import  org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;


@Service
public class ReservaServiceImpl implements ReservaService{

    private static final Logger logger = LoggerFactory.getLogger(ReservaServiceImpl.class);

    private final reservaRepository reservaRepository1;
    private final clienteRepository clienteRepository1;
    private final reservaClienteRepository reservaClienteRepository1;
    private final ReservaMapper reservaMapper;
    private final servicioPeluRepository servicioPeluRepository1;
    private final servicioRepository servicioRepository1;
    private final ReservaClienteMapper reservaClienteMapper;
    private final peluqueriaRepository peluqueriaRepository1;
    private final EmailService emailService;

    @Autowired
    public ReservaServiceImpl(ReservaMapper reservaMapper,
                              reservaClienteRepository reservaClienteRepository1,
                              reservaRepository reservaRepository1,
                              clienteRepository clienteRepository1,
                              ReservaClienteMapper reservaClienteMapper,
                              servicioPeluRepository servicioPeluRepository1,
                              servicioRepository servicioRepository1,
                              peluqueriaRepository peluqueriaRepository1,
                              EmailService emailService){
        this.reservaMapper = reservaMapper;
        this.clienteRepository1 = clienteRepository1;
        this.reservaRepository1 = reservaRepository1;
        this.reservaClienteRepository1 = reservaClienteRepository1;
        this.reservaClienteMapper = reservaClienteMapper;
        this.servicioPeluRepository1 = servicioPeluRepository1;
        this.servicioRepository1 = servicioRepository1;
        this.peluqueriaRepository1 = peluqueriaRepository1;
        this.emailService = emailService;
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
        LocalDate fechaHoy = LocalDate.now();
        if(dto.getFecha().isBefore(fechaHoy)) {
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

        //ESTO ES PARA ENVIAR EL MAIL AL CLIENTE QUE HA RESERVADO

        String emailPeluqueria = "noreply@peluqueriadev.com"; //Valor por defecto

        if(dto.getIdServicioPelu() != null) {
            ServicioPelu sp = servicioPeluRepository1.findById(dto.getIdServicioPelu()).orElse(null);
            if (sp != null) {
                Peluqueria p = peluqueriaRepository1.findById(sp.getPeluqueriaId()).orElse(null);
                if (p != null && p.getEmail() != null) {
                    emailPeluqueria = p.getEmail();
                }
            }
        }
        if(cliente.getEmail() != null && !cliente.getEmail().isEmpty()){
            emailService.enviarCorreoReserva(
                    cliente.getEmail(),
                    emailPeluqueria,
                    saved.getFecha().toString(),
                    saved.getHora().toString()
            );
        }

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
                .stream().map(reservaClienteMapper::toDto).toList();

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
                    logger.info("Reserva con id: {} cancelada", reservaId);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    //CONFIRMAR UNA RESERVA
    @Transactional
    @Override
    public ResponseEntity<ReservaDto> confirmaReserva(Integer reservaId) {

        return reservaRepository1.findById(reservaId)
                .map(reserva -> {
                    reserva.setEstado(EstadoReserva.CONFIRMADA);

                    Reserva saved = reservaRepository1.saveAndFlush(reserva);

                    ReservaDto dto = reservaMapper.toDto(saved);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
    @Override
    public List<ReservaNegocioDto> getReservasDePeluqueria(Integer peluqueriaId){
        List<Reserva> reservas = reservaRepository1.findByPeluqueriaId(peluqueriaId);
        return reservas.stream().map(reserva -> {

            // Resolvemos información de cliente y servicio usando helpers privados
            ClienteInfo clienteInfo = resolveClienteInfo(reserva);
            ServicioInfo servicioInfo = resolveServicioInfo(reserva);

            // DEVOLVEMOS EL DTO (que es lo que el negocio va a ver)
            return new ReservaNegocioDto(
                    reserva.getId(),
                    reserva.getFecha(),
                    reserva.getHora(),
                    clienteInfo.nombreCliente,
                    clienteInfo.emailCliente,
                    servicioInfo.nombreServicio,
                    servicioInfo.precio,
                    reserva.getEstado()
            );
        }).toList();
    }

    // Helper para obtener nombre y email del cliente asociado a una reserva
    private ClienteInfo resolveClienteInfo(Reserva reserva) {
        String nombreCliente = "Desconocido";
        String emailCliente = "-";

        List<ReservaCliente> rcs = reservaClienteRepository1.findByReserva_id(reserva.getId());
        if (rcs == null || rcs.isEmpty()) {
            return new ClienteInfo(nombreCliente, emailCliente);
        }

        ReservaCliente rc = rcs.get(0);
        if (rc == null) {
            return new ClienteInfo(nombreCliente, emailCliente);
        }

        Cliente cliente = clienteRepository1.findById(rc.getCliente_id()).orElse(null);
        if (cliente != null) {
            nombreCliente = cliente.getNombre() != null ? cliente.getNombre() : nombreCliente;
            emailCliente = (cliente.getEmail() != null && !cliente.getEmail().isEmpty()) ? cliente.getEmail() : emailCliente;
        }
        return new ClienteInfo(nombreCliente, emailCliente);
    }

    // Helper para obtener nombre y precio del servicio asociado a una reserva
    private ServicioInfo resolveServicioInfo(Reserva reserva) {
        String nombreServicio = "Servicio General";
        Integer precio = 0;

        if (reserva.getIdServicioPelu() == null) {
            return new ServicioInfo(nombreServicio, precio);
        }

        ServicioPelu sp = servicioPeluRepository1.findById(reserva.getIdServicioPelu()).orElse(null);
        if (sp == null) {
            return new ServicioInfo(nombreServicio, precio);
        }

        precio = sp.getPrecio() != null ? sp.getPrecio() : precio;
        Servicio s = servicioRepository1.findById(sp.getServicioId()).orElse(null);
        if (s != null && s.getNombre() != null) {
            nombreServicio = s.getNombre();
        }

        return new ServicioInfo(nombreServicio, precio);
    }

    // Clases auxiliares privadas para agrupar la información
    private static class ClienteInfo {
        final String nombreCliente;
        final String emailCliente;
        ClienteInfo(String nombreCliente, String emailCliente) {
            this.nombreCliente = nombreCliente;
            this.emailCliente = emailCliente;
        }
    }

    private static class ServicioInfo {
        final String nombreServicio;
        final Integer precio;
        ServicioInfo(String nombreServicio, Integer precio) {
            this.nombreServicio = nombreServicio;
            this.precio = precio;
        }
    }


}
