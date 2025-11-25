import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-contacto',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './contacto.html',
  styleUrl: './contacto.css',
})
export class Contacto {

   desarrollador = {
    nombre: 'Javier Quevedo', 
    email: 'javiquevedo09@gmail.com',
    ubicacion: 'Madrid, España'
  };

}
