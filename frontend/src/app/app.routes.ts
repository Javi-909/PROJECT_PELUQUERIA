import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home';
import { ContactoComponent } from './components/contacto/contacto';

// Esto define las "páginas" de tu aplicación
export const routes: Routes = [
  {
    path: '', // La ruta raíz (localhost:4200/)
    component: HomeComponent // Carga el componente Home
  },
  {
    path: 'contacto', // La ruta (localhost:4200/contacto)
    component: ContactoComponent // Carga el componente Contacto
  }
];