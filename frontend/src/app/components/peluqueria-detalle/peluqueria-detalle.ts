import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { PeluqueriaService } from '../../services/peluqueria';
import { Peluqueria } from '../../models/peluqueria.model';
import { Servicio } from '../../models/servicio.model';
import { ReservaService } from '../../services/reserva';
import { ReservaDto } from '../../models/reserva.model';


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
  private reservaService = inject(ReservaService);

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


  servicios: Servicio[] = [];

  servicioSeleccionadoId: number | null = null;

  constructor() { }

  ngOnInit(): void {
    // 1. Leer el ID de la URL
    const idParam = this.route.snapshot.paramMap.get('id');
    const id = Number(idParam);

    // 2. Validar ID
    if (!id || isNaN(id)) {
      console.error('❌ [Detalle] ID inválido o no numérico');
      this.errorMsg = 'ID de peluquería no válido en la URL.';
      return;
    }


    //metodo para obetener peluqueria con ID
    this.peluqueriaService.getPeluqueriaById(id).subscribe({
      next: (data) => {
        if (data) {
          this.peluqueria = data;
        } else {
          this.errorMsg = 'El backend devolvió datos vacíos.';
        }
      }

    });
    
    //metodo para mostrar los servicios de cada peluqueria
    this.peluqueriaService.getServiciosPeluqueria(id).subscribe({

        next: (data) => {

          this.servicios = data;
        },
        error: (err) => console.error('Error cargando servicios:', err)
    })
  }

  //metodo para cuando se hace click en un servicio
  seleccionarServicio(id: number){
    if(this.servicioSeleccionadoId === id){
      this.servicioSeleccionadoId = null; //lo descmarcamos si ya estaba seleccionado
    } else {
      this.servicioSeleccionadoId = id; //marcamos el nuevo
    }
  }


  //metodo para reservar una cita de peluqueria
  reservarCita(){

    if(this.peluqueria.id === 0) return;   //si no se ha cargado la peluqueria no hacemos nada

    //exigimos que se seleccione un servicio
    if(this.servicioSeleccionadoId === null){
      alert("Por favor, seleccione un servicio de la lista antes de reservar.");
      return;
    }

    const fechaInput = prompt("Introduce la fecha:", "2025-11-20");
    if(!fechaInput) return;

    const horaInput = prompt("Introduce la hora:", "10:00");
    if (!horaInput) return; // Si cancela, salimos


    const CLIENTE_ID_PRUEBA = 1; //demomento ponemos un cliente con id genérico, (luego habría que desarrollar login de clientes)

    const nuevaReserva: ReservaDto = {

      fecha: fechaInput,
      hora: horaInput,
      clienteId: CLIENTE_ID_PRUEBA,
      peluqueriaId: this.peluqueria.id,
      idServicioPelu: this.servicioSeleccionadoId
    };

    this.reservaService.crearReserva(CLIENTE_ID_PRUEBA, nuevaReserva).subscribe({   //metodo para crear reservas  (se le pasa como parametro clienteId y ReservaDto)
      next: (resp) => {
        alert('¡Reserva creada con éxito!');
      },
      error: (err) => {

        if(err.status === 409){ // este es el error que sucede cuando alguien intenta reservar en un servicio que ya esta reservado  (error 409 es por HttpStatus)
            alert("⚠️ NO SE PUDO RESERVAR:\n" + "Esa hora ya está ocupada para este servicio.");
        }

        else if(err.status === 500){
           alert("Error interno del servidor. Inténtalo más tarde.");
        }

        //Otros errores
        else {
            alert("Ocurrió un error desconocido al reservar.");
        }
      }
    });
  }

}