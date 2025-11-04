package com.example.demo.dto;


//clase auxiliar que se usa para  el metodo de listar los servicios de cada peluqueria.
//Mezcla servicioPeluDto con servicioDto

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
