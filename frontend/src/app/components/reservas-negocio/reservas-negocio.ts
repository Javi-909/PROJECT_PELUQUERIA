import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, ActivatedRoute } from '@angular/router';
import { ReservaService } from '../../services/reserva';
import { ReservaNegocio } from '../../models/reserva-negocio.model';

@Component({
  selector: 'app-reservas-negocio',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './reservas-negocio.html',
  styleUrl: './reservas-negocio.css',
})
export class ReservasNegocio implements OnInit {

   private route = inject(ActivatedRoute);
  private reservaService = inject(ReservaService);

  reservas: ReservaNegocio[] = [];
  peluqueriaId = 0;
  loading = true;

  ngOnInit() {
    // Cogemos el ID de la URL
    this.peluqueriaId = Number(this.route.snapshot.paramMap.get('id'));
    
    if(this.peluqueriaId) {
      this.cargarAgenda();
    }
  }

  cargarAgenda() {
    this.reservaService.getReservasDeNegocio(this.peluqueriaId).subscribe({
      next: (data) => {
        console.log('Agenda cargada:', data);
        this.reservas = data;
        // Ordenar por fecha y hora (más recientes primero)
        this.reservas.sort((a, b) => {
            const fechaA = new Date(a.fecha + 'T' + a.hora);
            const fechaB = new Date(b.fecha + 'T' + b.hora);
            return fechaA.getTime() - fechaB.getTime();
        });
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  aceptar(reserva: ReservaNegocio) {
    this.reservaService.confirmaReserva(reserva.id).subscribe({
      next: () => {
        // No hace falta alert, solo recargamos para ver el cambio de estado
        this.cargarAgenda(); 
      },
      error: () => alert("Error al confirmar la reserva.")
    });

}
  rechazar(reserva: ReservaNegocio) {
    if(confirm("¿Seguro que quieres rechazar esta reserva? Se eliminará de la agenda.")) {
      this.reservaService.cancelarReserva(reserva.id).subscribe({
        next: () => {
          this.cargarAgenda();
        },
        error: () => alert("Error al rechazar la reserva.")
      });
    }
  }

}
