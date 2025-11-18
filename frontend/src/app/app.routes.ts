import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home';
import { PeluqueriaCardComponent } from './components/peluqueria-card/peluqueria-card';
import { PeluqueriaDetalleComponent } from './components/peluqueria-detalle/peluqueria-detalle';
 

export const routes: Routes = [
  {
    path: '', // Cuando la ruta está vacía (inicio)
    component: HomeComponent
  },

  //Ruta dinámica: id es reemplazado por un número
  {path: 'peluqueria/:id', component: PeluqueriaDetalleComponent}

];