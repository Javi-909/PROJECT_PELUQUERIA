import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Login } from './login'; // Asegúrate de que el nombre de la clase y el archivo coincidan
import { Auth } from '../../services/auth';
import { Router, ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';

fdescribe('LoginComponent', () => {
  let component: Login;
  let fixture: ComponentFixture<Login>;

  // Declaramos los Mocks
  let mockAuthService: any;
  let mockRouter: any;

  beforeEach(async () => {
    // 1. Simulamos el servicio Auth
    mockAuthService = {
      login: jasmine.createSpy('login').and.returnValue(of({ 
        id: 1, 
        email: 'test@test.com', 
        role: 'CLIENTE', 
        token: 'fake-jwt' 
      }))
    };

    // 2. Simulamos el Router
    mockRouter = {
      navigate: jasmine.createSpy('navigate')
    };

    await TestBed.configureTestingModule({
      // Importamos el componente (es Standalone)
      imports: [Login],
      providers: [
        { provide: Auth, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter },
        // SOLUCIÓN AL ERROR 'root': Proveemos un mock de ActivatedRoute
        // Esto permite que el [routerLink] del HTML funcione sin explotar
        { 
          provide: ActivatedRoute, 
          useValue: { snapshot: { paramMap: new Map() } } 
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(Login);
    component = fixture.componentInstance;
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
    // 1. Preparamos los datos
    component.credentials.email = 'alexo@gmail.com';
    component.credentials.password = 'alexo';

    // 2. Ejecutamos
    component.onLogin();

    // 3. Verificamos llamada al servicio
    expect(mockAuthService.login).toHaveBeenCalledWith({
      email: 'alexo@gmail.com',
      password: 'alexo'
    });

    // 4. Verificamos navegación
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/home']);
  });

  it('debería mostrar un mensaje de error si las credenciales son incorrectas', () => {
    // Simulamos fallo 401
    mockAuthService.login.and.returnValue(throwError(() => ({ status: 401 })));

    component.onLogin();

    expect(component.errorMsg).toBe('Email o contraseña incorrectos.');
    expect(mockRouter.navigate).not.toHaveBeenCalled();
  });
});