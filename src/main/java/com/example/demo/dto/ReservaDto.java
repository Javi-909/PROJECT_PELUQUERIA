package com.example.demo.dto;


import com.example.demo.entity.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;



@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReservaDto {


    private LocalDate fecha;
    private LocalTime hora;
    private EstadoReserva estado;

    private Integer idServicioPelu;

    public Integer getIdServicioPelu() {
        return idServicioPelu; }
    public void setIdServicioPelu(Integer idServicioPelu) { this.idServicioPelu = idServicioPelu; }


    public LocalDate getFecha() {
        return fecha;}
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }


    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }


    public EstadoReserva getEstado(){
        return estado;
    }
    public void setEstado(EstadoReserva estado){
        this.estado = estado;
    }


}
