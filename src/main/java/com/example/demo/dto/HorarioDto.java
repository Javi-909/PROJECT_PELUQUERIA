package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;


@NoArgsConstructor
@AllArgsConstructor
@Builder

public class HorarioDto {

    private Time hora_apertura;
    private String dia_semana;
    private Time hora_cierre;
    //private Integer peluqueria_id;



    /*
    public Time getHoraApertura() {
        return hora_apertura;
    }


    public String getDiaSemana() {
        return dia_semana;
    }
    public Time getHoraCierre() {
        return hora_cierre;
    }

     */

    public String getDia_semana() {
        return dia_semana;
    }

    public Time getHora_cierre() {
        return hora_cierre;
    }

    public Time getHora_apertura() {
        return hora_apertura;
    }
    // Setters
    public void setHora_apertura(Time hora_apertura) {
        this.hora_apertura = hora_apertura;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

    public void setHora_cierre(Time hora_cierre) {
        this.hora_cierre = hora_cierre;
    }

    /*
    public void setHoraApertura(Time hora_apertura) {
         this.hora_apertura = hora_apertura;
    }


    public void setDiaSemana(String dia_semana) {
        this.dia_semana = dia_semana;
    }
    public void setHoraCierre(Time hora_cierre) {
        this.hora_cierre = hora_cierre;
    }

     */


}
