import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Login } from './login'; 
import { Auth } from '../../services/auth';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { provideRouter } from '@angular/router';

describe('LoginComponent', () => {
  let component: Login;
  let fixture: ComponentFixture<Login>;
  let router: Router;

  // Mock del servicio de Autenticación
  let mockAuthService: any;

  beforeEach(async () => {
    mockAuthService = {
      login: jasmine.createSpy('login').and.returnValue(of({ 
        id: 1, 
        email: 'test@test.com', 
        role: 'CLIENTE', 
        token: 'fake-jwt' 
      }))
    };

    await TestBed.configureTestingModule({
      imports: [Login],
      providers: [
        // 1. Usamos el proveedor real de Router para que [routerLink] funcione
        provideRouter([]),
        // 2. Mantenemos el mock de tu servicio Auth
        { provide: Auth, useValue: mockAuthService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(Login);
    component = fixture.componentInstance;
    
    // 3. Inyectamos el Router real del entorno de pruebas y espiamos su método 'navigate'
    router = TestBed.inject(Router);
    spyOn(router, 'navigate');

    fixture.detectChanges();
  });

  it('debería crearse el componente', () => {
    expect(component).toBeTruthy();
  });

  it('debería empezar con las credenciales vacías y sin mensaje de error', () => {
    expect(component.credentials.email).toBe('');
    expect(component.credentials.password).toBe('');
    expect(component.errorMsg).toBe('');
  });

  it('debería llamar al servicio Auth y navegar a /home cuando el login es correcto', () => {
    // Preparar datos
    component.credentials.email = 'alexo@gmail.com';
    component.credentials.password = 'alexo';

    // Ejecutar login
    component.onLogin();

    // Verificar llamada al servicio
    expect(mockAuthService.login).toHaveBeenCalledWith({
      email: 'alexo@gmail.com',
      password: 'alexo'
    });

    // Verificar que se intentó navegar al home
    expect(router.navigate).toHaveBeenCalledWith(['/home']);
  });

  it('debería mostrar un mensaje de error si las credenciales son incorrectas', () => {
    // Simulamos un error del servidor (401)
    mockAuthService.login.and.returnValue(throwError(() => ({ status: 401 })));

    component.onLogin();

    // El componente debería setear el mensaje de error
    expect(component.errorMsg).toBe('Email o contraseña incorrectos.');
    
    // No debería haber navegado
    expect(router.navigate).not.toHaveBeenCalled();
  });
});