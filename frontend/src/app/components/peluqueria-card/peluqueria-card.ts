import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Peluqueria } from '../../models/peluqueria.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-peluqueria-card',
  standalone: true,
  imports: [CommonModule,RouterLink],
  templateUrl: './peluqueria-card.html',
  styleUrls: ['./peluqueria-card.css']
})
export class PeluqueriaCardComponent {

  @Input() peluqueriaInfo: Peluqueria = {
   nombre: 'Nombre no disponible',
    email: 'email@no.disponible',
    direccion: 'Dirección no disponible',
    telefono: 0
  };

  constructor() { }

  verDetalles() {
    console.log('Viendo detalles de:', this.peluqueriaInfo.nombre);
  }
}
