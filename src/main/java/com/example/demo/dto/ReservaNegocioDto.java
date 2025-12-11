package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaNegocioDto {  //Este DTO sirve para poder mostrarle al negocio que reservas
    private Integer id;           // tiene en su negocio.
    private LocalDate fecha;
    private LocalTime hora;

    // Estos son los datos extra que necesita el negocio y que ReservaDto normal no tiene
    private String nombreCliente;
    private String emailCliente;
    private String nombreServicio;
    private Integer precioServicio;
}
