import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private http = inject(HttpClient);
  private router = inject(Router);

  
// URL del controlador AuthController
  private apiUrl = 'http://localhost:8080/auth/login';

  // Usamos una 'Signal' para tener el usuario accesible en toda la app
  currentUser = signal<any>(null);

  constructor() { 
    // Al iniciar la app, miramos si ya había alguien logueado en el navegador
    const storedUser = localStorage.getItem('app_user');
    if (storedUser) {
      this.currentUser.set(JSON.parse(storedUser));
    }
  }

  // 1. MÉTODO LOGIN
  login(credentials: {email: string, password: string}): Observable<any> {
    return this.http.post(this.apiUrl, credentials).pipe(
      tap((user: any) => {
        // Guardamos en memoria (Signal)
        this.currentUser.set(user);
        // Guardamos en disco (LocalStorage) para no perderlo al refrescar F5
        localStorage.setItem('app_user', JSON.stringify(user));
      })
    );
  }

  // 2. MÉTODO LOGOUT
  logout() {
    this.currentUser.set(null);
    localStorage.removeItem('app_user');
    this.router.navigate(['/']);
  }

  // 3. HELPER PARA OBTENER EL ID (Vital para reservar)
  get userId(): number {
    return this.currentUser()?.id || 0;
  }

  // 4. HELPER PARA SABER SI ESTÁ LOGUEADO
  isLoggedIn(): boolean {
    return this.currentUser() !== null;
  }

  // Método auxiliar para saber el Rol
  get role(): string {
    return this.currentUser()?.role || ''; 
  }

  isNegocio(): boolean {
    return this.role === 'NEGOCIO';
  }

  isCliente(): boolean {
    return this.role === 'CLIENTE';
  }

}
