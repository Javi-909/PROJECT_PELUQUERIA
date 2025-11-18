import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PeluqueriaCardComponent } from '../peluqueria-card/peluqueria-card';
import { PeluqueriaService } from '../../services/peluqueria';
import { Peluqueria } from '../../models/peluqueria.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, PeluqueriaCardComponent],
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class HomeComponent implements OnInit {

  private peluqueriaService = inject(PeluqueriaService);
  peluquerias: Peluqueria[] = [];

  constructor() { }

  ngOnInit(): void {
    this.cargarPeluquerias();
  }

  cargarPeluquerias() {
    this.peluqueriaService.getPeluquerias().subscribe({
      next: (data) => {
        this.peluquerias = data;
        console.log('Datos cargados:', data);
      },
      error: (err) => console.error('Error al conectar:', err)
    });
  }
}