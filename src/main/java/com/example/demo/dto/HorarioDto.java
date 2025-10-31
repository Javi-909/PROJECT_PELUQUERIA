package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class HorarioDto {

    private Time hora_apertura;
    private String dia_semana;
    private Time hora_cierre;

    public Time getHoraApertura() {
        return hora_apertura;
    }
    public String getDiaSemana() {
        return dia_semana;
    }
    public Time getHoraCierre() {
        return hora_cierre;
    }

    // Setters

    public void setHoraApertura(Time hora_apertura) {
         this.hora_apertura = hora_apertura;
    }
    public void setDiaSemana(String dia_semana) {
        this.dia_semana = dia_semana;
    }
    public void setHoraCierre(Time hora_cierre) {
        this.hora_cierre = hora_cierre;
    }
}
