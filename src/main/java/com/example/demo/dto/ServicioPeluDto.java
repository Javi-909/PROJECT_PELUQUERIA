package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ServicioPeluDto {


    private Integer precio;
    private Integer duracion;

    public Integer getPrecio() {
        return precio;
    }
    public Integer getDuracion() {
        return duracion;
    }

    // Setters

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

}
