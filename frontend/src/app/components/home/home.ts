import { Component, OnInit, inject } from '@angular/core'; // 1. Importa OnInit
import { CommonModule } from '@angular/common';
import { PeluqueriaCardComponent } from '../peluqueria-card/peluqueria-card';
import { RouterLink } from '@angular/router'; // Para el botón de "Contacto"

// 2. Importaciones corregidas
import { PeluqueriaService } from '../../services/peluqueria';
import { Peluqueria } from '../../models/peluqueria.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, PeluqueriaCardComponent, RouterLink], // RouterLink es para el botón de Contacto
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class HomeComponent implements OnInit { // 3. Implementa OnInit

  private peluqueriaService = inject(PeluqueriaService);

  peluquerias: Peluqueria[] = [];
  
  constructor() { }

  // 4. ngOnInit se ejecuta al cargar el componente
  ngOnInit(): void {
    this.cargarPeluquerias();
  }

  // 5. Este método llama al servicio y rellena el array
  cargarPeluquerias(): void {
    this.peluqueriaService.getPeluquerias().subscribe({
      next: (datos) => {
        this.peluquerias = datos; // Asignamos los datos de la API
        console.log('Peluquerías cargadas desde la API:', datos);
      },
      error: (error) => {
        console.error('Error al cargar peluquerías:', error);
      }
    });
  }
}