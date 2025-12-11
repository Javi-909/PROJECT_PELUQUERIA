import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home';
import { PeluqueriaCardComponent } from './components/peluqueria-card/peluqueria-card';
import { PeluqueriaDetalleComponent } from './components/peluqueria-detalle/peluqueria-detalle';
import { Contacto } from './components/contacto/contacto';
import { PeluqueriaForm } from './components/peluqueria-form/peluqueria-form';
import { NuevoServicio } from './components/nuevo-servicio/nuevo-servicio';
import { Login } from './components/login/login';
import { MiPerfil } from './components/mi-perfil/mi-perfil';
import { RegistroComponent } from './components/registro/registro';
import { ReservasNegocio } from './components/reservas-negocio/reservas-negocio';
 

export const routes: Routes = [
  {
    path: '', // Cuando la ruta está vacía (inicio)
    component: Login
  },

  //Ruta dinámica: id es reemplazado por un número
  {path: 'peluqueria/:id', component: PeluqueriaDetalleComponent},

  {path: 'contacto', component: Contacto},

  {path: 'nueva-peluqueria', component: PeluqueriaForm},

  {path: 'peluqueria/:peluqueriaId/nuevo-servicio', component: NuevoServicio},

  {path: 'home', component: HomeComponent},

  {path: 'mi-perfil', component: MiPerfil},

  {path: 'registro', component: RegistroComponent},

  {path: 'peluqueria/:id/reservas-negocio', component: ReservasNegocio}


];