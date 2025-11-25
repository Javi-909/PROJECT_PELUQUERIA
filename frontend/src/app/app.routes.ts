import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home';
import { PeluqueriaCardComponent } from './components/peluqueria-card/peluqueria-card';
import { PeluqueriaDetalleComponent } from './components/peluqueria-detalle/peluqueria-detalle';
import { Contacto } from './components/contacto/contacto';
import { PeluqueriaForm } from './components/peluqueria-form/peluqueria-form';
 

export const routes: Routes = [
  {
    path: '', // Cuando la ruta está vacía (inicio)
    component: HomeComponent
  },

  //Ruta dinámica: id es reemplazado por un número
  {path: 'peluqueria/:id', component: PeluqueriaDetalleComponent},

  {path: 'contacto', component: Contacto},

  {path: 'nueva-peluqueria', component: PeluqueriaForm}

];