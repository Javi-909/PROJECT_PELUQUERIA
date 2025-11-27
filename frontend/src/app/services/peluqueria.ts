import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Peluqueria } from '../models/peluqueria.model';
import { Servicio } from '../models/servicio.model';


@Injectable({
  providedIn: 'root'
})
export class PeluqueriaService {
  
  private http = inject(HttpClient);
  
  // URL de tu Backend (ajustada a tu controlador)
  private apiUrl = 'http://localhost:8080/peluqueria';

  // URL base para servicios genéricos
  private apiServiciosUrl = 'http://localhost:8080/servicio'

  constructor() { }

  // Método para obtener la lista de todas las peluquerias
  getPeluquerias(): Observable<Peluqueria[]> {
    return this.http.get<Peluqueria[]>(`${this.apiUrl}/findAll`);
  }


  //Llamada a metodo para mostrar la peluqueria con id (cuando se clicka en "Ver detalle")
  getPeluqueriaById(id: number): Observable<Peluqueria>{

    return this.http.get<Peluqueria>(`${this.apiUrl}/${id}`);
  }

  //Llamada a metodo para mostrar los servicios de cada peluqueria
  getServiciosPeluqueria(id: number): Observable<Servicio[]>{

    return this.http.get<Servicio[]>(`${this.apiUrl}/${id}/servicios`);

  }


  //Llamada a metodo para crear peluqueria
  createPeluqueria(peluqueria: Peluqueria): Observable<Peluqueria>{
    return this.http.post<Peluqueria>(`${this.apiUrl}/create`, peluqueria);
  }


  getServiciosDisponibles(): Observable<any[]>{
    //Devuelve la url de todos los serviciios (findAll)
    return this.http.get<any[]>(`${this.apiServiciosUrl}/findAll`);
  }

}