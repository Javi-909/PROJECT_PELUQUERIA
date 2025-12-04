package com.example.demo.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponseDto {   //este es el objeto que devolvemos al front si el login es correcto
                                  // para que sepa que menús mostrar dependiendo del rol
    private Integer id;
    private String nombre;
    private String email;
    private String role; // Puede ser "CLIENTE" o "NEGOCIO"
    private String token;
}