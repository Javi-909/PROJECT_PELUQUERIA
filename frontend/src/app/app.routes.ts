import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home';

export const routes: Routes = [
  {
    path: '', // Cuando la ruta está vacía (inicio)
    component: HomeComponent
  },
  // Aquí añadiremos más rutas en el futuro (detalle, contacto, etc.)
];