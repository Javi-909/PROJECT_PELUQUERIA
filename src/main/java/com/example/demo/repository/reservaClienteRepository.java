package com.example.demo.repository;


import com.example.demo.entity.ReservaCliente;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface reservaClienteRepository extends JpaRepository<ReservaCliente, Integer> {


    //lo he hecho por problemas de convenio de nombres entre (cliente_id y clienteId, igual que reserva_id)
    @Query(value = "SELECT * FROM reservacliente rc WHERE rc.cliente_id = :clienteId", nativeQuery = true)
    List<ReservaCliente>findByClienteId(@Param("clienteId")Integer cliente_id);

    @Query(value = "SELECT * FROM reservacliente rc WHERE rc.reserva_id = :reservaId", nativeQuery = true)
    List<ReservaCliente>findByReserva_id(@Param("reservaId") Integer reserva_id);


    @Modifying //como voy a modificar algo de la BBDD, tengo que poner esta anotacion
    @Transactional
    @Query(value = "DELETE from reservacliente rc WHERE rc.reserva_id = :reservaId", nativeQuery = true)
    void deleteByReservaId(@Param("reservaId")Integer reservaId);

}
