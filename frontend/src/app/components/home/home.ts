import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PeluqueriaCardComponent } from '../peluqueria-card/peluqueria-card';
import { PeluqueriaService } from '../../services/peluqueria';
import { Peluqueria } from '../../models/peluqueria.model';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, PeluqueriaCardComponent, FormsModule, RouterLink],
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class HomeComponent implements OnInit {

  private peluqueriaService = inject(PeluqueriaService);
  peluquerias: Peluqueria[] = [];

  //Lista que muestra las peluquerias (resultado del filtro)
  peluqueriasFiltradas: Peluqueria[] = [];

  //Lo que escribe el usuario en la barra de búsqueda
  searchTerm: string = '';

  constructor() { }

  ngOnInit(): void {
    this.cargarPeluquerias();
  }

  cargarPeluquerias() {
    this.peluqueriaService.getPeluquerias().subscribe({
      next: (data) => {
        this.peluquerias = data;
        this.peluqueriasFiltradas = data;
        console.log('Datos cargados:', data);
      },
      error: (err) => console.error('Error al conectar:', err)
    });
  }
  //metodo que se ejecuta cada vez que escribes una letra

filtrarResultados() {
    if (!this.searchTerm) {
      // Si el buscador está vacío, mostramos todas
      this.peluqueriasFiltradas = this.peluquerias;
    } else {
      // Filtramos: Que el nombre incluya lo que has escrito (ignorando mayúsculas/minúsculas)
      const termino = this.searchTerm.toLowerCase();
      
      this.peluqueriasFiltradas = this.peluquerias.filter(pelu => 
        pelu.nombre.toLowerCase().includes(termino) || 
        pelu.direccion.toLowerCase().includes(termino) // (Opcional) También busca por dirección
      );
    }
  }
}