package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReservaClienteDto {

    private Integer cliente_id;
    private Integer reserva_id;

    public Integer getCliente_id(){
        return cliente_id;
    }

    public Integer getReserva_id(){
        return reserva_id;
    }

    public void setReserva_id(Integer reserva_id){
        this.reserva_id = reserva_id;
    }

    public void setCliente_id(Integer cliente_id){
        this.cliente_id = cliente_id;
    }
}
