import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { Auth } from '../../services/auth';
import { ClienteService, Cliente } from '../../services/cliente';


@Component({
  selector: 'app-mi-perfil',
  standalone: true,
  imports: [CommonModule, FormsModule,RouterLink],
  templateUrl: './mi-perfil.html',
  styleUrl: './mi-perfil.css',
})
export class MiPerfil implements OnInit {

  private authService = inject(Auth);
  private clienteService = inject(ClienteService);
  private router = inject(Router);


   cliente: Cliente = {
    nombre: '',
    email: '',
    genero:'',
    password: '' // Empezamos vacío. Si lo deja vacío, no se cambia.
  };

  successMsg = '';

  ngOnInit(): void {
    const id = this.authService.userId;
    if (id) {
      this.clienteService.getClienteById(id).subscribe({
        next: (data) => {
          this.cliente = data;
          this.cliente.password = ''; // Limpiamos la pass por seguridad visual
        },
        error: (err) => console.error(err)
      });
    }
  }


  guardarCambios() {
    const id = this.authService.userId;
    
    this.clienteService.updateCliente(id, this.cliente).subscribe({
      next: () => {
        this.successMsg = '¡Perfil actualizado correctamente!';
        // Limpiamos el mensaje a los 3 segundos
        setTimeout(() => this.successMsg = '', 3000);
      },
      error: () => alert("Error al actualizar el perfil")
    });
  }
  
  cerrarSesion() {
      this.authService.logout();
  }


}
