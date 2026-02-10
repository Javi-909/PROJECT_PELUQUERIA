import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

// Servicios
import { ClienteService } from '../../services/cliente';
import { PeluqueriaService } from '../../services/peluqueria';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './registro.html',
  styleUrls: ['./registro.css']
})
export class RegistroComponent {

  private clienteService = inject(ClienteService);
  private peluqueriaService = inject(PeluqueriaService);
  private router = inject(Router);

  // Estado del switch: 'CLIENTE' o 'NEGOCIO'
  tipoRegistro: 'CLIENTE' | 'NEGOCIO' = 'CLIENTE';

  // Objeto único para el formulario (sirve para ambos)
  datos = {
    nombre: '',
    email: '',
    password: '',
    telefono: 0,
    genero: '', //solo para cliente
    direccion: '' // Solo se usa si es negocio
  };

  registrar() {
    // Validaciones básicas
    if (!this.datos.nombre || !this.datos.email || !this.datos.password) {
      alert("Por favor, rellena los campos obligatorios.");
      return;
    }

    //VALIDACION DE MAIL (que sea formato correcto)
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.datos.email)) {
      alert("Por favor, ingresa un correo electrónico válido.");
      return;
    }

    console.log(`Registrando como ${this.tipoRegistro}...`, this.datos);

    if (this.tipoRegistro === 'CLIENTE') {
      // 1. REGISTRO DE CLIENTE
      this.clienteService.createCliente({
        nombre: this.datos.nombre,
        email: this.datos.email,
        password: this.datos.password,
        telefono: this.datos.telefono,
        genero: this.datos.genero
      }).subscribe({
        next: () => this.exito(),
        error: (err) => this.error(err)
      });

    } else {
      // 2. REGISTRO DE NEGOCIO
      this.peluqueriaService.createPeluqueria({
        nombre: this.datos.nombre,
        email: this.datos.email,
        password: this.datos.password,
        telefono: this.datos.telefono,
        direccion: this.datos.direccion // Campo extra
      }).subscribe({
        next: () => this.exito(),
        error: (err) => this.error(err)
      });
    }
  }

  exito() {
    alert('¡Registro completado! Ahora puedes iniciar sesión.');
    this.router.navigate(['/login']);
  }

  error(err: any) {
    console.error(err);
    alert('Hubo un error al registrarse. Revisa los datos o si el email ya existe.');
  }
}