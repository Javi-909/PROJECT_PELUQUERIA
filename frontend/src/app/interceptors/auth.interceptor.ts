import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  
  // 1. Recuperamos el usuario del almacenamiento (donde está el token)
  const storedUser = localStorage.getItem('app_user');
  
  if (storedUser) {
    const user = JSON.parse(storedUser);
    const token = user.token;

    // 2. Si hay token, clonamos la petición y le pegamos la cabecera
    if (token) {
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      // Dejamos pasar la petición modificada con la "pulsera" puesta
      return next(authReq);
    }
  }

  // Si no hay token, dejamos pasar la petición tal cual (ej: login)
  return next(req);
};