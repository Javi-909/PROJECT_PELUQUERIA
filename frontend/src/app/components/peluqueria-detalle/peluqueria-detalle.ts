import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink, Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; // Necesario para los inputs de fecha
import { PeluqueriaService } from '../../services/peluqueria';
import { ReservaService } from '../../services/reserva';
import { Peluqueria } from '../../models/peluqueria.model';
import { Servicio } from '../../models/servicio.model';
import { ReservaDto } from '../../models/reserva.model';
import { ServicioPeluService } from '../../services/serviciopelu';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-peluqueria-detalle',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './peluqueria-detalle.html',
  styleUrls: ['./peluqueria-detalle.css']
})
export class PeluqueriaDetalleComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private peluqueriaService = inject(PeluqueriaService);
  private reservaService = inject(ReservaService);
  private servicioPeluService = inject(ServicioPeluService);
  public authService = inject(Auth);


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
  fechaSeleccionada = '';
  horaSeleccionada = '';

  //Variables para AÑADIR NUEVO SERVICIO   EN PROCESOO
  mostrarFormularioServicio = false;
  serviciosGnericos: Servicio[] = [];
  nuevoServicioForm = {
    servicioId: null,
    precio: 0,
    duracion: 0
  };
  errorMsg = '';

  //constructor() { }

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
      this.cargarServiciosDeLaPeluqueria(id);
    }
  }
  // --- Metodo para cargar servicios de cada peluqueria ---
  cargarServiciosDeLaPeluqueria(id: number) {
    this.peluqueriaService.getServiciosPeluqueria(id).subscribe({
      next: (data) => this.servicios = data,
      error: (err) => console.error('Error cargando servicios:', err)
    });
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

    const clienteIdReal = this.authService.userId;

     if (clienteIdReal === 0) {
      alert("🔒 Para reservar, necesitas iniciar sesión.");
      this.router.navigate(['/login']);
      return;
    }

    const nuevaReserva: ReservaDto = {
      fecha: this.fechaSeleccionada,
      hora: this.horaSeleccionada + ":00",
      clienteId: clienteIdReal,
      peluqueriaId: this.peluqueria.id || 0,
      idServicioPelu: this.servicioSeleccionadoId 
    };


    this.reservaService.crearReserva(clienteIdReal, nuevaReserva).subscribe({
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