package com.example.demo.repository;

import com.example.demo.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface reservaRepository extends JpaRepository<Reserva, Integer> {


    List<Reserva> findByIdServicioPeluAndFechaAndHora(Integer idServicioPelu, LocalDate fecha, LocalTime hora);

    @Query(value= "SELECT r.* FROM reserva r " +
            "JOIN reservacliente rc ON r.id = rc.reserva_id " +
            "WHERE rc.cliente_id = :clienteId",
            nativeQuery = true)
    List<Reserva> findByClienteId(@Param("clienteId") Integer clienteId);


    // Busca las reservas de una determinada peluqueria
    @Query(value = "SELECT r.* FROM reserva r " +
            "JOIN serviciopelu sp ON r.serviciopelu_id = sp.id " +
            "WHERE sp.peluqueria_id = :peluqueriaId", nativeQuery = true)
    List<Reserva> findByPeluqueriaId(@Param("peluqueriaId") Integer peluqueriaId);

}
