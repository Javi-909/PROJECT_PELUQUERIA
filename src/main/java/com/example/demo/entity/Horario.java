package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;


@Entity
@Table(name = "horario")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private Integer peluqueria_id;
    private Time hora_apertura;
    private Time hora_cierre;
    private String dia_semana;

    public Integer getId() {
        return id;
    }
    public Integer getPeluqueriaId() {
        return peluqueria_id;
    }
    public Time getHoraApertura() {
        return hora_apertura;
    }
    public Time getHoraCierre() {
        return hora_cierre;
    }
    public String getDiaSemana(){
        return dia_semana;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }
        public void setPeluqueriaId(Integer peluqueria_id) {
        this.peluqueria_id = peluqueria_id;
    }
    public void setHoraApertura(Time hora_apertura) {
        this.hora_apertura = hora_apertura;
    }
    public void setHoraCierre(Time hora_cierre) {
        this.hora_cierre = hora_cierre;
    }
    public void setDiaSemana(String dia_semana) {
        this.dia_semana = dia_semana;
    }




}
