package com.example.demo.repository;



import com.example.demo.entity.ReservaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface reservaClienteRepository extends JpaRepository<ReservaCliente, Integer> {


    //lo he hecho por problemas de convenio de nombres entre (cliente_id y clienteId, igual que reserva_id)
    @Query(value = "SELECT r.id, r.fecha, r.hora,r.nombre " +
            "FROM reservacliente rc " +
            "JOIN reserva r  ON rc.reserva_id = r.id " +
            "JOIN serviciopelu sp ON r.serviciopelu_id = sp.id " +
            "JOIN servicio s ON sp.servicio_id = s.id " +
            "WHERE rc.cliente_id = :clienteId", nativeQuery = true)
    List<ReservaCliente>findByClienteId(@Param("clienteId")Integer cliente_id);

    @Query(value = "SELECT * FROM reservacliente rc WHERE rc.reserva_id = :reservaId", nativeQuery = true)
    List<ReservaCliente>findByReserva_id(@Param("reservaId") Integer reserva_id);

}
