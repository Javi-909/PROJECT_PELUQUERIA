import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Peluqueria } from '../models/peluqueria.model';

@Injectable({
  providedIn: 'root'
})
export class PeluqueriaService {
  
  private http = inject(HttpClient);
  
  // URL de tu Backend (ajustada a tu controlador)
  private apiUrl = 'http://localhost:8080/peluqueria/findAll';

  constructor() { }

  // Método para obtener la lista
  getPeluquerias(): Observable<Peluqueria[]> {
    return this.http.get<Peluqueria[]>(this.apiUrl);
  }
}