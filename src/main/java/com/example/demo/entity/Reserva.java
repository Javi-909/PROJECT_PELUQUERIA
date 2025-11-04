package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private Date fecha;
    private Time hora;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoReserva estado; //Pendiente, Confirmada, Cancelada

    @Column(name = "serviciopelu_id")
    private Integer idServicioPelu;

    public Integer getId() {
        return id;
    }
    public Date getFecha() {
        return fecha;
    }
    public Time getHora() {
        return hora;
    }
    public EstadoReserva getEstado() {
        return estado;
    }
    public Integer getIdServicioPelu() {
        return idServicioPelu;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setHora(Time hora) {
        this.hora = hora;
    }
    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
    public void setIdServicioPelu(Integer idServicioPelu) {
        this.idServicioPelu = idServicioPelu;
    }



}
