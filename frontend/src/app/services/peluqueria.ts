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
  private apiUrl = 'http://localhost:8080/peluqueria';

  constructor() { }

  // Método para obtener la lista de todas las peluquerias
  getPeluquerias(): Observable<Peluqueria[]> {
    return this.http.get<Peluqueria[]>(`${this.apiUrl}/findAll`);
  }


  //metodo para mostrar la peluqueria con id (cuando se clicka en "Ver detalle")
  getPeluqueriaById(id: number): Observable<Peluqueria>{

    return this.http.get<Peluqueria>(`${this.apiUrl}/${id}`);
  }


}