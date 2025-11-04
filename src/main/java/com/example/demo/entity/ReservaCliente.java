package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reservacliente")
public class ReservaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cliente_id")
    private Integer cliente_id;

    @Column(name = "reserva_id")
    private Integer reserva_id;


    public Integer getId(){
        return id;
    }

    public Integer getCliente_id(){
        return cliente_id;
    }
    public Integer getReserva_id(){
        return reserva_id;
    }

    public void setId(Integer id){
        this.id = id;
    }
    public void setCliente_id(Integer cliente_id){
        this.cliente_id = cliente_id;
    }
    public void setReserva_id(Integer reserva_id){
        this.reserva_id = reserva_id;
    }


}
