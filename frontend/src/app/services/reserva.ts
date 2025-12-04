import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReservaDto } from '../models/reserva.model';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {

  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/reserva'; 

  constructor() { }

  crearReserva(clienteId: number, reserva: ReservaDto): Observable<any> {
    return this.http.post(`${this.apiUrl}/create/${clienteId}`, reserva);
  }

  getReservasPorCliente(clienteId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${clienteId}`);

}

  cancelarReserva(reservaId: number): Observable<any> {
      return this.http.delete(`${this.apiUrl}/cancelareserva/${reservaId}`);
    }

}