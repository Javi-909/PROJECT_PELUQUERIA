import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common'; // Importante para directivas básicas
import { Peluqueria } from '../../models/peluqueria.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-peluqueria-card',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './peluqueria-card.html',
  styleUrls: ['./peluqueria-card.css']
})
export class PeluqueriaCardComponent {

  // Recibe datos del padre
  @Input() peluqueriaInfo!: Peluqueria;

 
}