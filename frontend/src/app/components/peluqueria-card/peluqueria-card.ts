import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-peluqueria-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './peluqueria-card.html',
  styleUrls: ['./peluqueria-card.css']
})
export class PeluqueriaCardComponent {

  @Input() peluqueriaInfo: { id: number, nombre: string, direccion: string } = {
    id: 0,
    nombre: 'Nombre no disponible',
    direccion: 'Dirección no disponible'
  };

  constructor() { }

  verDetalles() {
    console.log('Viendo detalles de:', this.peluqueriaInfo.nombre);
  }
}
