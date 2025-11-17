import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Peluqueria } from '../models/peluqueria.model'; // Importamos el modelo SIN ID

@Injectable({
  providedIn: 'root'
})
export class PeluqueriaService {

  private http = inject(HttpClient);

  // Esta es la URL que coincide 100% con tu PeluqueriaController
  private apiUrl = 'http://localhost:8080/peluqueria/findAll';

  constructor() { }

  getPeluquerias(): Observable<Peluqueria[]> {
    // Hacemos la petición GET a la URL correcta
    return this.http.get<Peluqueria[]>(this.apiUrl);
  }
}