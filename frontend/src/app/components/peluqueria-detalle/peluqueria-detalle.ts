import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { PeluqueriaService } from '../../services/peluqueria';
import { Peluqueria } from '../../models/peluqueria.model';

@Component({
  selector: 'app-peluqueria-detalle',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './peluqueria-detalle.html',
  styleUrls: ['./peluqueria-detalle.css']
})
export class PeluqueriaDetalleComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private peluqueriaService = inject(PeluqueriaService);

  // Variable para mostrar mensajes de error en el HTML si falla
  errorMsg: string = '';

  // Inicializamos con datos vacíos
  peluqueria: Peluqueria = {
    id: 0,
    nombre: '',
    email: '',
    direccion: '',
    telefono: 0
  };

  constructor() { }

  ngOnInit(): void {
    // 1. Leer el ID de la URL
    const idParam = this.route.snapshot.paramMap.get('id');
    console.log('🔎 [Detalle] ID recibido en URL:', idParam);

    const id = Number(idParam);

    // 2. Validar ID
    if (!id || isNaN(id)) {
      console.error('❌ [Detalle] ID inválido o no numérico');
      this.errorMsg = 'ID de peluquería no válido en la URL.';
      return;
    }

    // 3. Llamar al Backend
    console.log(`📡 [Detalle] Pidiendo datos al backend para ID: ${id}...`);
    
    this.peluqueriaService.getPeluqueriaById(id).subscribe({
      next: (data) => {
        console.log('✅ [Detalle] Datos recibidos del backend:', data);
        if (data) {
          this.peluqueria = data;
        } else {
          this.errorMsg = 'El backend devolvió datos vacíos.';
        }
      },
      error: (err) => {
        console.error('❌ [Detalle] Error en la petición HTTP:', err);
        this.errorMsg = 'Error de conexión con el servidor (Revisa la consola).';
      }
    });
  }
}