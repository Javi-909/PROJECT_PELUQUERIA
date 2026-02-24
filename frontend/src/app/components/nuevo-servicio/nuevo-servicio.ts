import { Component, OnInit, inject } from '@angular/core';
import { PeluqueriaService } from '../../services/peluqueria';
import { ServicioPeluService } from '../../services/serviciopelu';
import { ServicioPeluCreacionDto } from '../../models/servicioPeluCreacion.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';



@Component({
  selector: 'app-nuevo-servicio',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './nuevo-servicio.html',
  styleUrl: './nuevo-servicio.css',
})
export class NuevoServicio implements OnInit {

private route = inject(ActivatedRoute);
private router = inject(Router);
private peluqueriaService = inject(PeluqueriaService);
private servicioPeluService = inject(ServicioPeluService);

peluqueriaId = 0;
  serviciosGenericos: any[] = [];
  
  // Modelo del formulario
  nuevoServicio = {
    servicioId: null, // Aquí guardaremos el ID del servicio genérico seleccionado
    precio: 0,
    duracion: 30
  };

  //constructor() { }

  ngOnInit(): void {
    // 1. Obtener el ID de la peluquería de la URL
    const idParam = this.route.snapshot.paramMap.get('peluqueriaId');
    this.peluqueriaId = Number(idParam);

    if (!this.peluqueriaId) {
      alert("Error: No se ha especificado la peluquería.");
      this.router.navigate(['/']);
      return;
    }

    // 2. Cargar el catálogo de servicios genéricos
    this.cargarCatalogoServicios();
  }

  cargarCatalogoServicios() {
    this.peluqueriaService.getServiciosDisponibles().subscribe({
      next: (data) => {
        this.serviciosGenericos = data;
        console.log('Catálogo cargado:', data);
      },
      error: (err) => console.error('Error cargando catálogo', err)
    });
  }
  

  guardar() {
    // Validaciones
    if (!this.nuevoServicio.servicioId) {
      alert("Por favor, selecciona un tipo de servicio.");
      return;
    }
    if (this.nuevoServicio.precio <= 0 || this.nuevoServicio.duracion <= 0) {
      alert("El precio y la duración deben ser mayores que 0.");
      return;
    }

    // Montar el DTO
    const dto: ServicioPeluCreacionDto = {
      peluqueriaId: this.peluqueriaId,
      servicioId: Number(this.nuevoServicio.servicioId),
      precio: this.nuevoServicio.precio,
      duracion: this.nuevoServicio.duracion
    };

    console.log('Enviando:', dto);

    // Llamar al backend
    this.servicioPeluService.addServicio(dto).subscribe({
      next: () => {
        alert("¡Servicio añadido correctamente!");
        // Volver a la página de detalle de la peluquería
        this.router.navigate(['/peluqueria', this.peluqueriaId]);
      },
      error: (err) => {
        console.error(err);
        alert("Error al guardar. Revisa la consola.");
      }
    });
  }
}




