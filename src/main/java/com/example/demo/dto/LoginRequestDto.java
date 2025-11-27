package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {   //Este es el objeto que recibe los datos que envia el ususario desde el formulario (para loguearse)
    private String email;
    private String password;
}