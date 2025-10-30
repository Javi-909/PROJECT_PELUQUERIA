package com.example.demo.controller;


import com.example.demo.dto.PeluqueriaDto;
import com.example.demo.dto.ServicioResponseDto;
import com.example.demo.service.ServicioService;
import com.example.demo.service.PeluqueriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;







import java.util.List;

@RestController
@RequestMapping("/peluqueria")
public class PeluqueriaController {

    @Autowired
    private PeluqueriaService peluqueriaService;
    private ServicioService servicioService;

    @GetMapping("/findAll")
    public List<PeluqueriaDto> findAll() {
        return peluqueriaService.findAll(); //esto devuelve la lista de peluqueriaDto
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeluqueriaDto> mostrarPeluqueriaPorId(@PathVariable Integer id){
        return peluqueriaService.mostrarPeluqueriaPorId(id);
    }

    @PostMapping("/create") //hay que añadir metodo crearServiciosPelu, porque cuando se crea una pelu no tiene servicios
    public PeluqueriaDto createPeluqueria(@RequestBody PeluqueriaDto peluqueriaDto) {
        return peluqueriaService.createPeluqueria(peluqueriaDto);
    }

    @GetMapping("/{id}/servicios")
    public ResponseEntity<List<ServicioResponseDto>> listarServiciosPorPeluqueria(@PathVariable Integer id){
        return peluqueriaService.listarServiciosPorPeluqueria(id);
    }

    //@DeleteMapping("/delete")
    //public void deletePeluqueria(@PathVariable Long id) {
        //peluqueriaService.deletePeluqueria(id);
    }



    // ...otros métodos (mostrarClienteId,actualizarClienteId...)

