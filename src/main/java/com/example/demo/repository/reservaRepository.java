package com.example.demo.repository;

import com.example.demo.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface reservaRepository extends JpaRepository<Reserva, Integer> {


    List<Reserva> findByIdServicioPeluAndFechaAndHora(Integer idServicioPelu, Date fecha, Time hora);

    @Query(value= "SELECT r.* FROM reserva r " +
            "JOIN reservacliente rc ON r.id = rc.reserva_id " +
            "WHERE rc.cliente_id = :clienteId",
            nativeQuery = true)
    List<Reserva> findByClienteId(@Param("clienteId") Integer clienteId);



}
