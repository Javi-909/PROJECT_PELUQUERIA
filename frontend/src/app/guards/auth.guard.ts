import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { Auth } from '../services/auth';

export const authGuard: CanActivateFn = (route, state) => {  //esto es para que se verfique si 
                                                             // el usuario esta logueado 
  
  const authService = inject(Auth);
  const router = inject(Router);

  // 1. Preguntamos al servicio: ¿Hay usuario guardado?
  if (authService.isLoggedIn()) {
    return true; // ¡Adelante, pasa!
  } else {
    // 2. Si no, te echo al Login
    router.navigate(['/']);
    return false;
  }
};
