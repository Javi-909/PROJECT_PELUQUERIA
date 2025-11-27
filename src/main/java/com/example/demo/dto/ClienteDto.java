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
    private String password;

    public String getNombre() {
        return nombre;
    }
    public String getEmail() {
        return email;
    }
    public String getGenero() {
        return genero;
    }

    public String getPassword() {
        return password;
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

    public void setPassword(String password){
        this.password = password;
    }
}
