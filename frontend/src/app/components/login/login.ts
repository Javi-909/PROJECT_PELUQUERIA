import { CommonModule } from '@angular/common';
import { Component, inject} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Auth } from '../../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  private authService = inject(Auth);
  private router = inject(Router);


   credentials = {
    email: '',
    password: ''
  };

  errorMsg = '';

  onLogin() {
    // Llamamos al servicio
    this.authService.login(this.credentials).subscribe({
      next: (data) => {
        console.log('Login correcto:', data);
        // Si todo va bien, vamos al Home
        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Email o contraseña incorrectos.';
      }
    });
  }
}


