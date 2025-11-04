package com.example.demo.dto;


import com.example.demo.entity.EstadoReserva;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReservaDto {

    //para poner en POSTMAN el body del JSON de esta forma
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fecha;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time hora;
    //private EstadoReserva estado;

    @Column(name = "serviciopelu_id")
    private Integer idServicioPelu;

    public Integer getIdServicioPelu() {
        return idServicioPelu; }
    public void setIdServicioPelu(Integer idServicioPelu) { this.idServicioPelu = idServicioPelu; }


    public Date getFecha() {
        return fecha;}
    public void setFecha(Date fecha) { this.fecha = fecha; }


    public Time getHora() { return hora; }
    public void setHora(Time hora) { this.hora = hora; }

    /*
    public EstadoReserva getEstado(){
        return estado;
    }
    public void setEstado(EstadoReserva estado){
        this.estado = estado;
    }
*/

}
