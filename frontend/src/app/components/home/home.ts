import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Necesario para *ngFor

// Importa la tarjeta (sin la extensión .ts)
import { PeluqueriaCardComponent } from '../peluqueria-card/peluqueria-card';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, PeluqueriaCardComponent],

  // --- CORRECCIÓN CLAVE ---
  // Estos nombres deben coincidir con tus archivos
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class HomeComponent {

  // Datos de EJEMPLO (esto estaba bien)
  peluquerias = [
    {
      id: 1,
      nombre: 'Peluquería Estilo Total',
      direccion: 'Calle Falsa 123, Ciudad'
    },
    {
      id: 2,
      nombre: 'Cortes Modernos',
      direccion: 'Avenida Siempre Viva 456'
    },
    {
      id: 3,
      nombre: 'El Rincón del Peinado',
      direccion: 'Plaza Central 789'
    }
  ];

  constructor() { }
}
