package com.example.demo.repository;



import com.example.demo.entity.ReservaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface reservaClienteRepository extends JpaRepository<ReservaCliente, Integer> {


    //lo he hecho por problemas de convenio de nombres entre (cliente_id y clienteId, igual que reserva_id)
    @Query(value = "SELECT * FROM reservacliente rc WHERE rc.cliente_id =: clienteId", nativeQuery = true)
    List<ReservaCliente>findByCliente_id(Integer cliente_id);

    @Query(value = "SELECT * FROM reservacliente rc WHERE rc.reserva_id =: reservaId", nativeQuery = true)
    List<ReservaCliente>findByReserva_id(Integer reserva_id);

}
