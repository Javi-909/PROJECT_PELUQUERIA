package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "serviciopelu")
public class ServicioPelu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer peluqueria_id;
    private Integer servicio_id;
    private Integer precio;
    private Integer duracion;

    public Integer getId() {
        return id;
    }
    public Integer getPeluqueriaId() {
        return peluqueria_id;
    }
    public Integer getServicioId() {
        return servicio_id;
    }
    public Integer getPrecio() {
        return precio;
    }
    public Integer getDuracion() {
        return duracion;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setPeluqueria_id(Integer peluqueria_id) {
        this.peluqueria_id = peluqueria_id;
    }
    public void setServicioId(Integer servicio_id) {
        this.servicio_id = servicio_id;
    }
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }


}
