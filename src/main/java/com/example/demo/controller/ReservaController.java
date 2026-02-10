package com.example.demo.controller;


import com.example.demo.dto.ReservaClienteDto;
import com.example.demo.dto.ReservaDto;
import com.example.demo.dto.ReservaNegocioDto;
import com.example.demo.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;


@RestController
@RequestMapping("/reserva")
public class ReservaController {

    //@Autowired
    private ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }


    @PostMapping("/create/{clienteId}")
   public ReservaDto createReserva(@PathVariable Integer clienteId, @RequestBody ReservaDto dto){
        return reservaService.createReserva(clienteId,dto);
   }

   @GetMapping("/{clienteId}")
   public ResponseEntity<List<ReservaClienteDto>> findByClienteId(@PathVariable Integer clienteId){
                return ResponseEntity.ok(reservaService.findByClienteId(clienteId));
   }

    @DeleteMapping("/cancelareserva/{reservaId}")
   public ResponseEntity<ReservaDto> cancelaReserva(@PathVariable Integer reservaId){
       return reservaService.cancelaReserva(reservaId);
   }

   @GetMapping("/peluqueria/{peluqueriaId}")
    public ResponseEntity<List<ReservaNegocioDto>> getReservasDeNegocio(@PathVariable Integer peluqueriaId){
        return ResponseEntity.ok(reservaService.getReservasDePeluqueria(peluqueriaId));
   }

   @PutMapping("/confirmar/{reservaId}")
    public ResponseEntity<ReservaDto> confirmaReserva(@PathVariable Integer reservaId){
        return reservaService.confirmaReserva(reservaId);
   }


}
