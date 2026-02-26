import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReservaDto } from '../models/reserva.model';
import { ReservaNegocio } from '../models/reserva-negocio.model';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {

  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/reserva'; 

  //constructor() { }

  crearReserva(clienteId: number, reserva: ReservaDto): Observable<ReservaDto> {
    return this.http.post<ReservaDto>(`${this.apiUrl}/create/${clienteId}`, reserva);
  }

  getReservasPorCliente(clienteId: number): Observable<ReservaDto[]> {
    return this.http.get<ReservaDto[]>(`${this.apiUrl}/${clienteId}`);

}

  cancelarReserva(reservaId: number): Observable<ReservaDto> {
      return this.http.delete<ReservaDto>(`${this.apiUrl}/cancelareserva/${reservaId}`);
    }

    getReservasDeNegocio(peluqueriaId: number): Observable<ReservaNegocio[]> {
    return this.http.get<ReservaNegocio[]>(`${this.apiUrl}/peluqueria/${peluqueriaId}`);
  }

  confirmaReserva(reservaId: number): Observable<ReservaDto> {
    return this.http.put<ReservaDto>(`${this.apiUrl}/confirmar/${reservaId}`, null);

}
}