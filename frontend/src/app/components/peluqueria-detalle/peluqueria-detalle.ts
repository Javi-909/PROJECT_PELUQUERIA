import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms'; // Necesario para los inputs de fecha
import { PeluqueriaService } from '../../services/peluqueria';
import { ReservaService } from '../../services/reserva';
import { Peluqueria } from '../../models/peluqueria.model';
import { Servicio } from '../../models/servicio.model';
import { ReservaDto } from '../../models/reserva.model';

@Component({
  selector: 'app-peluqueria-detalle',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './peluqueria-detalle.html',
  styleUrls: ['./peluqueria-detalle.css']
})
export class PeluqueriaDetalleComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private peluqueriaService = inject(PeluqueriaService);
  private reservaService = inject(ReservaService);

  // Inicializamos con datos vacíos para que el HTML no falle nunca
  peluqueria: Peluqueria = {
    id: 0,
    nombre: '',
    email: '',
    direccion: '',
    telefono: 0
  };
  
  servicios: Servicio[] = [];
  servicioSeleccionadoId: number | null = null;
  
  // Variables para el formulario
  fechaSeleccionada: string = '';
  horaSeleccionada: string = '';

  errorMsg: string = '';

  constructor() { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    if (id && !isNaN(id)) {
      // 1. Cargar Info
      this.peluqueriaService.getPeluqueriaById(id).subscribe({
        next: (data) => {
          if (data) this.peluqueria = data;
        },
        error: () => this.errorMsg = 'Error al cargar peluquería.'
      });

      // 2. Cargar Servicios
      this.peluqueriaService.getServiciosPeluqueria(id).subscribe({
        next: (data) => this.servicios = data,
        error: (err) => console.error(err)
      });
    }
  }

  seleccionarServicio(id: number) {
    if (this.servicioSeleccionadoId === id) {
      this.servicioSeleccionadoId = null;
    } else {
      this.servicioSeleccionadoId = id;
    }
  }

  reservarCita() {
    // Validaciones
    if (this.peluqueria.id === 0) return;
    
    if (this.servicioSeleccionadoId === null) {
      alert("⚠️ Por favor, selecciona un servicio de la lista.");
      return;
    }

    if (!this.fechaSeleccionada || !this.horaSeleccionada) {
      alert("⚠️ Por favor, selecciona fecha y hora.");
      return;
    }

    const CLIENTE_ID_MOCK = 1;

    const nuevaReserva: ReservaDto = {
      fecha: this.fechaSeleccionada,
      hora: this.horaSeleccionada + ":00",
      clienteId: CLIENTE_ID_MOCK,
      peluqueriaId: this.peluqueria.id || 0,
      idServicioPelu: this.servicioSeleccionadoId 
    };

    console.log(`Enviando reserva...`, nuevaReserva);

    this.reservaService.crearReserva(CLIENTE_ID_MOCK, nuevaReserva).subscribe({
      next: () => {
        alert('✅ ¡Cita reservada con éxito!');
        // Resetear formulario
        this.servicioSeleccionadoId = null;
        this.fechaSeleccionada = '';
        this.horaSeleccionada = '';
      },
      error: (err) => {
        console.error('Error:', err);
        if (err.status === 409) alert("⚠️ Esa hora ya está ocupada.");
        else alert("Error al reservar.");
      }
    });
  }
}