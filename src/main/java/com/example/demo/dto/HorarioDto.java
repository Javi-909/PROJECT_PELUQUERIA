package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class HorarioDto {

    private int hora_apertura;
    private String dia_semana;
    private int hora_cierre;
}
