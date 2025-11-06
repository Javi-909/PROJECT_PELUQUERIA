package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ClienteDto {

    private String nombre;
    private String email;
    private String genero;

    public String getNombre() {
        return nombre;
    }
    public String getEmail() {
        return email;
    }
    public String getGenero() {
        return genero;
    }

    // Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
}
