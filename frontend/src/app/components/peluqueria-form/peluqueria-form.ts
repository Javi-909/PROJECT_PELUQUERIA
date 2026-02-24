import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { PeluqueriaService } from '../../services/peluqueria';
import { Peluqueria } from '../../models/peluqueria.model';

@Component({
  selector: 'app-peluqueria-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './peluqueria-form.html',
  styleUrl: './peluqueria-form.css',
})
export class PeluqueriaForm {

  private peluqueriaService = inject(PeluqueriaService);
  private router = inject(Router);


  // Objeto vacío para rellenar en el formulario
  // No ponemos ID porque el backend lo genera
  nuevaPeluqueria: Peluqueria = {
    nombre: '',
    direccion: '',
    email: '',
    password: '',
    telefono: 0
  };

  guardarPeluqueria(){
    if(!this.nuevaPeluqueria.nombre || !this.nuevaPeluqueria.direccion || !this.nuevaPeluqueria.password){
      alert('Por favor, rellena al menos el nombre, la dirección y la contraseña.');
      return;

    }
  

  this.peluqueriaService.createPeluqueria(this.nuevaPeluqueria).subscribe({
    next: () => {
        alert('¡Peluquería creada con éxito!');
        // Redirigir al Home para ver la nueva peluquería en la lista
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error('Error al crear:', err);
        alert('Hubo un error al guardar la peluquería.');
      }
    });
  }
}
