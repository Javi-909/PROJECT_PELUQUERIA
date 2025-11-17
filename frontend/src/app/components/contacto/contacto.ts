import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-contacto',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './contacto.html',
  styleUrls: ['./contacto.css'],
})
export class ContactoComponent {

  nombre = "Javier Quevedo";
  email = "jquevesa@emeal.nttdata.com";

  constructor() { }

}
