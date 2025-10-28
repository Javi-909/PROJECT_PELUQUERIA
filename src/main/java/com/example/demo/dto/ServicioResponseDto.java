package com.example.demo.dto;

public class ServicioResponseDto {
    private String nombre;
    private String descripcion;
    private Integer precio;
    private Integer duracion;

    public ServicioResponseDto(String nombre, String descripcion, Integer precio, Integer duracion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracion = duracion;
    }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Integer getPrecio() { return precio; }
    public Integer getDuracion() { return duracion; }
}
