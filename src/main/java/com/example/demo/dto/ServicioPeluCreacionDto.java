package com.example.demo.dto;

/*
clase auxiliar de servicioPeluDto para poder añadir
servicioPelu en peluquerias (porque al hacer POST en postman habia que
añadir los 4 parametros, y servicioPeluDto solo tenia dos.
*/

public class ServicioPeluCreacionDto {
    private Integer servicioId;
    private Integer peluqueriaId;
    private Integer precio;
    private Integer duracion;

    public ServicioPeluCreacionDto(Integer servicioId, Integer peluqueriaId, Integer precio, Integer duracion) {
        this.servicioId = servicioId;
        this.peluqueriaId = peluqueriaId;
        this.precio = precio;
        this.duracion = duracion;
    }
    public Integer getServicioId() { return servicioId; }
    public Integer getPeluqueriaId() { return peluqueriaId; }
    public Integer getPrecio() { return precio; }
    public Integer getDuracion() { return duracion; }
}


