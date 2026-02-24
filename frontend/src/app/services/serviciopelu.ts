import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ServicioPeluCreacionDto } from '../models/servicioPeluCreacion.model';

@Injectable({
  providedIn: 'root'
})
export class ServicioPeluService {
    
  private http = inject(HttpClient);
  // URL base correcta para este controlador
  private apiUrl = 'http://localhost:8080/serviciopelu';

  //constructor() { }

  addServicio(dto: ServicioPeluCreacionDto): Observable<any> {
    // La URL final será: http://localhost:8080/serviciopelu/add
    return this.http.post(`${this.apiUrl}/add`, dto);
  }


}