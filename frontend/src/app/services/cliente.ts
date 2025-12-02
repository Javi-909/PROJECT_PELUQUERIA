import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Creamos una interfaz rápida aquí o en models/cliente.model.ts
export interface Cliente {
  id?: number;
  nombre: string;
  email: string;
  genero: string;
  password?: string; // Opcional
  telefono?: number;
}

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/cliente';

  constructor() { }

  getClienteById(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiUrl}/${id}`);
  }

  updateCliente(id: number, cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.apiUrl}/actualizar/${id}`, cliente);
  }

  createCliente(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(`${this.apiUrl}/create`,cliente)
  }

}